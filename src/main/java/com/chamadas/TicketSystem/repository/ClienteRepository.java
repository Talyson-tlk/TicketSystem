package com.chamadas.TicketSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamadas.TicketSystem.model.Cliente;


//Repositório JPA para gerenciar clientes.

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
