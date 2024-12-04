package com.chamadas.TicketSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Getter
@Setter
@Entity
//Representa o histórico de ações realizadas em um ticket, incluindo a data e descrição da ação.
public class TicketHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    
	@JsonView(Views.IdOnly.class)
    private String acao; // Descrição da ação realizada
    
	@JsonView(Views.IdOnly.class)
    private LocalDateTime dataAcao; // Data e hora da ação

    // Construtores, Getters e Setters
}
