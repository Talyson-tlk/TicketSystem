package com.chamadas.TicketSystem.repository;

import com.chamadas.TicketSystem.model.HorarioAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório JPA para gerenciar horários de atendimento.

public interface HorarioAtendimentoRepository extends JpaRepository<HorarioAtendimento, Long> {
}
