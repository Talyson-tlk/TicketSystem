package com.chamadas.TicketSystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.chamadas.TicketSystem.enums.Criticidade;
import com.chamadas.TicketSystem.enums.Impacto;
import com.chamadas.TicketSystem.enums.Prioridade;
import com.chamadas.TicketSystem.enums.Status;

import jakarta.persistence.Enumerated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
	
	@NotBlank
	private String titulo;
	
	@NotBlank
    private String descricao;
	
	@Enumerated
    private Status status;
	
	@Enumerated
    private Prioridade prioridade;
	
	@NotBlank
    private Long clienteId;
    
	@NotBlank
    private Long filaId; // Nova associação com Fila
	
	@NotBlank
    private Long agenteId; // Novo campo para associar um agente
	
    private List<TicketHistoricoDTO> historico; // Lista de ações no ticket
	
	@Enumerated
    private Impacto impacto;
    
	@Enumerated
    private Criticidade criticidade;


}
