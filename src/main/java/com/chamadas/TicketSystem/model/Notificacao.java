package com.chamadas.TicketSystem.model;

import java.time.LocalDateTime;

import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//Representa notificações enviadas para clientes ou agentes, com informações como mensagem e data de envio.
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdOnly.class)
    private Long id;
    
    @JsonView(Views.IdOnly.class)
    private String mensagem;
    
    @JsonView(Views.IdOnly.class)
    private boolean visualizada = false; // Para notificações no painel de controle
    
    @JsonView(Views.IdOnly.class)
    private LocalDateTime dataEnvio;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // Notificações para o cliente, caso aplicável
    
    @ManyToOne
    @JoinColumn(name = "agente_id")
    private Agente agente;
    
   
}
