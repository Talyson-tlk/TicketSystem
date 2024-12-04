package com.chamadas.TicketSystem.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.chamadas.TicketSystem.enums.TipoNota;
import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nota")
@Getter
@Setter
//Representa notas associadas a um ticket, incluindo tipo, conteúdo e data de criação.
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdOnly.class)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @JsonView(Views.IdOnly.class)
    private TipoNota tipoNota;

    @NotBlank(message = "O conteúdo da nota é obrigatório")
    @Size(max = 1000, message = "O conteúdo da nota deve ter no máximo 1000 caracteres")
    @JsonView(Views.IdOnly.class)
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonView(Views.IdOnly.class)
    private Ticket ticket;

    @CreationTimestamp
    @PastOrPresent(message = "A data de criação deve ser no passado ou no presente")
    @JsonView(Views.IdOnly.class)
    private LocalDateTime dataCriacao;

    // Getters e Setters
}

