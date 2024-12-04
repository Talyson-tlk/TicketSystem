package com.chamadas.TicketSystem.repository;

import com.chamadas.TicketSystem.model.TicketHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório JPA para gerenciar histórico de ações em tickets.

public interface TicketHistoricoRepository extends JpaRepository<TicketHistorico, Long> {
}
