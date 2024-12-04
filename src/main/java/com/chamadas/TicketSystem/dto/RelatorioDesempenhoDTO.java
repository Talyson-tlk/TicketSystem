package com.chamadas.TicketSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDesempenhoDTO {
    private int totalTickets;
    private long tempoMedioResolucao;  // Em minutos
    private int ticketsCumpridosSLA;
    private int ticketsNaoCumpridosSLA;

}