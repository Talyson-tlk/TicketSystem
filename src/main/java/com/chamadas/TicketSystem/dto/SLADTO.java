package com.chamadas.TicketSystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import com.chamadas.TicketSystem.model.Fila;
import com.chamadas.TicketSystem.model.SLA;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SLADTO {

    private Long filaId;
    private Duration tempoResposta;
    private Duration tempoResolucao;

    public SLA toEntity() {
        SLA sla = new SLA();
        Fila fila = new Fila();
        fila.setId(filaId);  // Define o ID da fila existente
        sla.setFila(fila);
        sla.setTempoResposta(tempoResposta);
        sla.setTempoResolucao(tempoResolucao);
        return sla;
    }

    // Getters e Setters
}
