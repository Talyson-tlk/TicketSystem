package com.chamadas.TicketSystem.dto;

import javax.validation.constraints.NotBlank;

import com.chamadas.TicketSystem.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FecharTicketDTO {
	
	@NotBlank
    private Status status;
	
	@NotBlank
    private String motivo;
	
}
