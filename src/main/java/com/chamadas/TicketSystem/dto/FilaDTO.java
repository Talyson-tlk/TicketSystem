package com.chamadas.TicketSystem.dto;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilaDTO {
	
	@NotBlank
    private String nome;
	
	@NotBlank
    private String descricao; 
	
    private List<Long> ticketIds; // IDs dos tickets associados

	
}
