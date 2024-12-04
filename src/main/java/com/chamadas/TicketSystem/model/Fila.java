package com.chamadas.TicketSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.chamadas.TicketSystem.views.Views;
import com.chamadas.TicketSystem.views.ViewsFila;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//Representa uma fila de atendimento, contendo nome, descrição e tickets associados.
public class Fila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdOnly.class)
    private Long id;
    
    @NotBlank(message = "O nome é obrigatório")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	@Column(nullable = false, length = 100)
    @JsonView(ViewsFila.FilaOnly.class)
    private String nome;
    
    @NotBlank(message = "A descrição é obrigatória")
	@Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @JsonView(ViewsFila.FilaOnly.class)
    private String descricao;

    @OneToMany(mappedBy = "fila", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    
    @Transient // Indica que este campo não será persistido no banco de dados
    @JsonView(ViewsFila.FilaOnly.class)
    public List<Long> getTicketIds() {
        return tickets.stream().map(Ticket::getId).collect(Collectors.toList());
    }

}

