package com.chamadas.TicketSystem.model;


import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
//Representa os clientes no sistema, com informações como nome e e-mail.
public class Cliente {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @JsonView(Views.IdOnly.class)
	   private Long id;

	   @NotBlank(message = "O nome é obrigatório")
	   @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	   @Column(nullable = false, length = 100)
	   private String nome;

	   @NotBlank(message = "O email é obrigatório")
	   @Email(message = "Email deve ser válido")
	   @Size(max = 150, message = "O email deve ter no máximo 150 caracteres")
	   @Column(nullable = false, unique = true, length = 150)
	   private String email;

}
