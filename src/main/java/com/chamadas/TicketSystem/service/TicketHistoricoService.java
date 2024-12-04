package com.chamadas.TicketSystem.service;

import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.model.TicketHistorico;
import com.chamadas.TicketSystem.repository.TicketHistoricoRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//Serviço para registrar ações no histórico de tickets.
public class TicketHistoricoService {

    @Autowired
    private TicketHistoricoRepository ticketHistoricoRepository;

    public void registrarAcao(Ticket ticket, String acao) {
        TicketHistorico historico = new TicketHistorico();
        historico.setTicket(ticket); // Atribui o ticket diretamente
        historico.setAcao(acao);
        historico.setDataAcao(LocalDateTime.now());

        ticketHistoricoRepository.save(historico);
    }
}
