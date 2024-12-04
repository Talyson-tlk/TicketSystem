package com.chamadas.TicketSystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.chamadas.TicketSystem.views.ViewsAgente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//Representa os agentes no sistema, incluindo nome, e-mail e os tickets associados.
public class Agente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(ViewsAgente.AgenteOnly.class)
    private Long id;
    
	@JsonView(ViewsAgente.AgenteOnly.class)
    private String nome;
	
	@JsonView(ViewsAgente.AgenteOnly.class)
    private String email;

    @OneToMany(mappedBy = "agente")
    private List<Ticket> tickets = new ArrayList<>(); // Inicializa como uma lista vazia

    @Transient // Indica que este campo não será persistido no banco de dados
	@JsonView(ViewsAgente.AgenteOnly.class)
    public List<Long> getTicketIds() {
        return tickets.stream().map(Ticket::getId).collect(Collectors.toList());
    }
}
