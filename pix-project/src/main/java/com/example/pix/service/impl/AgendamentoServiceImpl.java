package com.example.pix.service.impl;

import com.example.pix.domain.Agendamento;
import com.example.pix.dto.AgendamentoRequestDTO;
import com.example.pix.dto.AgendamentoResponseDTO;
import com.example.pix.dto.AnaliseFraudeResponse;
import com.example.pix.enums.EventoAgendamento;
import com.example.pix.enums.StatusFraude;
import com.example.pix.integration.AntiFraudClient;
import com.example.pix.mapper.AgendamentoMapper;
import com.example.pix.repository.AgendamentoRepository;
import com.example.pix.service.AgendamentoService;
import com.example.pix.service.AuditoriaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {


    private final AgendamentoRepository repo;
    private final AntiFraudClient antiFraudClient;
    private final AuditoriaService auditoriaService;

    public AgendamentoServiceImpl(AgendamentoRepository repo, AntiFraudClient antiFraudClient, AuditoriaService auditoriaService) {
        this.repo = repo;
        this.antiFraudClient = antiFraudClient;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO req,String correlationId) {
        Map<String, Object> payload = buildPayload(req);
        AnaliseFraudeResponse analise = antiFraudClient.checkFraud(payload);
        String status = analise.getStatus();
        String motivo = analise.getMotivo();

        Agendamento agendamento = buildAgendamento(req, status,motivo,correlationId);
        registrarAuditoriaFraude(agendamento.getIdCorrelacao(),analise, payload);

        String evento = status.equals(StatusFraude.REJEITADO.name()) ? EventoAgendamento.AGENDAMENTO_REJEITADO.toString()  : EventoAgendamento.AGENDAMENTO_CRIADO.toString();
        registrarAuditoriaEvento(agendamento.getIdCorrelacao(), evento, req, status);

        Agendamento salvo = repo.save(agendamento);
        return AgendamentoMapper.toResponseDTO(salvo, evento + " - " + motivo);
    }

    public AgendamentoResponseDTO consultarAgendamento(UUID id) {
        Agendamento agendamento = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        return AgendamentoMapper.toResponseDTO(agendamento, "Consulta realizada");
    }

    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return repo.findAll().stream()
                .map(ag -> AgendamentoMapper.toResponseDTO(ag, "Consulta realizada"))
                .collect(Collectors.toList());
    }

    private Map<String, Object> buildPayload(AgendamentoRequestDTO req) {
        return Map.of(
                "valor", req.getValor(),
                "chavePagador", req.getChavePagador(),
                "chaveRecebedor", req.getChaveRecebedor(),
                "moeda", req.getMoeda()
        );
    }

    private Agendamento buildAgendamento(AgendamentoRequestDTO req, String status,String motivo, String correlationId) {
        OffsetDateTime agora = OffsetDateTime.now();
        return Agendamento.builder()
                .idCorrelacao(correlationId)
                .valor(req.getValor())
                .moeda(req.getMoeda())
                .chavePagador(req.getChavePagador())
                .nomePagador(req.getNomePagador())
                .chaveRecebedor(req.getChaveRecebedor())
                .nomeRecebedor(req.getNomeRecebedor())
                .periodicidade(req.getPeriodicidade())
                .dataInicio(req.getDataInicio())
                .dataFim(req.getDataFim())
                .status(status)
                .motivo(motivo)
                .criado(agora)
                .atualizado(agora)
                .build();
    }

    private void registrarAuditoriaFraude(String idCorrelacao, AnaliseFraudeResponse analise, Map<String, Object> payload) {
        auditoriaService.registrar(
                idCorrelacao,
                EventoAgendamento.ANALISE_FRAUDE.toString(),
                analise.getStatus() != null ? analise.getStatus() : null,
                analise.getMotivo(),
                payload.toString()
        );
    }

    private void registrarAuditoriaEvento(String idCorrelacao, String evento, AgendamentoRequestDTO req, String status) {
        String motivo = evento.equals(EventoAgendamento.AGENDAMENTO_REJEITADO.toString())
                ? "Agendamento rejeitado por suspeita de fraude"
                : "Agendamento criado com status: " + status;

        auditoriaService.registrar(
                idCorrelacao,
                evento,
                status,
                motivo,
                req.toString()
        );
    }

}