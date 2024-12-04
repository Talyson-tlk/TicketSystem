package com.chamadas.TicketSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamadas.TicketSystem.model.Agente;

//Repositório JPA para gerenciar agentes.

public interface AgenteRepository extends JpaRepository<Agente, Long> {
}
