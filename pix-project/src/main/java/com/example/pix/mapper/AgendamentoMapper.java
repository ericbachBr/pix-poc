package com.example.pix.mapper;

import com.example.pix.domain.Agendamento;
import com.example.pix.dto.AgendamentoResponseDTO;

public class AgendamentoMapper {

    public static AgendamentoResponseDTO toResponseDTO(Agendamento ag, String mensagem) {
        return new AgendamentoResponseDTO(
                ag.getId(),
                ag.getIdCorrelacao(),
                ag.getStatus(),
                mensagem,
                ag.getAtualizado(),
                ag.getChavePagador(),
                ag.getNomePagador(),
                ag.getChaveRecebedor(),
                ag.getNomeRecebedor(),
                ag.getMoeda(),
                ag.getValor(),
                ag.getPeriodicidade(),
                ag.getDataInicio(),
                ag.getDataFim(),
                ag.getMotivo(),
                ag.getCriado(),
                ag.getAtualizado()
        );
    }


}
