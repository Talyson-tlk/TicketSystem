package com.chamadas.TicketSystem.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardTendenciasDTO {
    private Map<String, Long> ticketsAbertosPorMes; 
    private Map<String, Long> ticketsFechadosComSucessoPorMes; 
    private Map<String, Long> ticketsFechadosSemSucessoPorMes;
}
