package com.chamadas.TicketSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamadas.TicketSystem.model.Nota;

//Repositório JPA para gerenciar notas associadas a tickets.

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByTicketId(Long ticketId);

}
