package com.chamadas.TicketSystem.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoricoDTO {
	
	@NotBlank
    private String acao;
	
	@NotBlank
    private LocalDateTime dataAcao;
	
}
