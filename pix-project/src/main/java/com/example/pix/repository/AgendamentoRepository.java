package com.example.pix.repository;

import com.example.pix.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
}