package com.chamadas.TicketSystem.model;



import java.time.Duration;  // Alteração aqui

import com.chamadas.TicketSystem.views.ViewsSLA;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//Representa um SLA, definindo tempos de resposta e resolução para uma fila específica.
public class SLA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(ViewsSLA.SLAOnly.class)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fila_id")
	@JsonView(ViewsSLA.SLAOnly.class)
    private Fila fila;
    
	@JsonView(ViewsSLA.SLAOnly.class)
	private Duration tempoResposta;  // Tempo para uma primeira resposta
	
	@JsonView(ViewsSLA.SLAOnly.class)
	private Duration tempoResolucao; // Tempo para resolução do chamado

    // Getters e Setters
}
