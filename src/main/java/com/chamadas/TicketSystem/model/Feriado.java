package com.chamadas.TicketSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
//Representa feriados cadastrados, com data e descrição.
public class Feriado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data; // Data do feriado
    private String descricao; // Descrição do feriado

}
