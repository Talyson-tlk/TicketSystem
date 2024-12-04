package com.chamadas.TicketSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.chamadas.TicketSystem.enums.Criticidade;
import com.chamadas.TicketSystem.enums.Impacto;
import com.chamadas.TicketSystem.enums.Prioridade;
import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.enums.Status;
import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//Representa um ticket, incluindo título, descrição, status, prioridade, e associações a cliente, fila e agente.
public class Ticket {
	

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @JsonView(Views.IdOnly.class)
	 private Long id;

	 @NotBlank(message = "O título é obrigatório")
	 @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
	 @JsonView(Views.IdOnly.class)
	 private String titulo;

	 @NotBlank(message = "A descrição é obrigatória")
	 @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
	 @JsonView(Views.IdOnly.class)
	 private String descricao;


	 @Enumerated(EnumType.STRING)
	 @Column(length = 20)
	 @JsonView(Views.IdOnly.class)
	 private Status status;

	 @Enumerated(EnumType.STRING)
	 @Column(length = 20)
	 @JsonView(Views.IdOnly.class)
	 private Prioridade prioridade;
	 
	@Enumerated(EnumType.STRING)
	private Impacto impacto;

	@Enumerated(EnumType.STRING)
	private Criticidade criticidade;

	 @CreationTimestamp
	 @JsonView(Views.IdOnly.class)
	 private LocalDateTime dataCriacao;

	 @ManyToOne(fetch = FetchType.EAGER) // Carregar a entidade Cliente ao mesmo tempo
	 @JoinColumn(name = "cliente_id", nullable = false)
	 @JsonView(Views.IdOnly.class)
	 private Cliente cliente;
	 
	 @ManyToOne
	 @JoinColumn(name = "fila_id")
	 @JsonView(Views.IdOnly.class)
	 private Fila fila;

	 private LocalDateTime prazoFinal; // Novo campo para armazenar o prazo final
	 
	 @Enumerated(EnumType.STRING)
	 @JsonView(Views.IdOnly.class)
	 private SlaStatus slaStatus = SlaStatus.DENTRO_DO_PRAZO; // Novo campo
	 
	 @ManyToOne
	 @JoinColumn(name = "agente_id")
	 private Agente agente;
	 
	 @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	 @JsonView(Views.IdOnly.class)
	 private List<TicketHistorico> historico = new ArrayList<>();
	 
	 private LocalDateTime dataUltimaAtividade; // Última vez que houve interação com o ticket




}
