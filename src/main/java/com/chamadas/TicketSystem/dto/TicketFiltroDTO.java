package com.chamadas.TicketSystem.dto;

import com.chamadas.TicketSystem.enums.Prioridade;
import com.chamadas.TicketSystem.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class TicketFiltroDTO {
    private Status status;
    private Prioridade prioridade;
    private Long clienteId;
    private Long agenteId;
    private LocalDateTime dataCriacaoInicio;
    private LocalDateTime dataCriacaoFim;
}
