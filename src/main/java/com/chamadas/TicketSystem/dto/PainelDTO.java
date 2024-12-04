package com.chamadas.TicketSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PainelDTO {
    private int totalTicketsAbertos;
    private int ticketsVencendoSLA;
    private int ticketsVencidosSLA;
    private int notificacoesNaoVisualizadas;

}
