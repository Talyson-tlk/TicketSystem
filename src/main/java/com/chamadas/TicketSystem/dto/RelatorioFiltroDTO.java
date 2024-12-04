package com.chamadas.TicketSystem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioFiltroDTO {
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Long agenteId;
    private Long filaId;

    // Getters e Setters
}