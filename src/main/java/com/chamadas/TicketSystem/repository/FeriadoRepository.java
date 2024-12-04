package com.chamadas.TicketSystem.repository;

import com.chamadas.TicketSystem.model.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório JPA para gerenciar feriados.

public interface FeriadoRepository extends JpaRepository<Feriado, Long> {
}
