package com.chamadas.TicketSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamadas.TicketSystem.model.SLA;

//Repositório JPA para gerenciar SLAs.

public interface SLARepository extends JpaRepository<SLA, Long> {
    Optional<SLA> findByFilaId(Long filaId);
}
