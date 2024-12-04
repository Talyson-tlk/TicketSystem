package com.chamadas.TicketSystem.dto;

import javax.validation.constraints.NotBlank;

import com.chamadas.TicketSystem.enums.TipoNota;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaDTO {
	
	@Enumerated
    private TipoNota tipoNota;
    
	@NotBlank
	private String conteudo;
	
	@NotBlank
    private Long ticketId;
}
