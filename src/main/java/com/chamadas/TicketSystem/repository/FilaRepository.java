package com.chamadas.TicketSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamadas.TicketSystem.model.Fila;

//Repositório JPA para gerenciar filas.

public interface FilaRepository extends JpaRepository<Fila, Long> {
}
