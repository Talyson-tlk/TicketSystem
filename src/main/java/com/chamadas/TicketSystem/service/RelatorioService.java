package com.chamadas.TicketSystem.service;

import com.chamadas.TicketSystem.dto.RelatorioFiltroDTO;
import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.dto.RelatorioDesempenhoDTO;
import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
//Serviço para geração de relatórios de desempenho.
public class RelatorioService {

    @Autowired
    private TicketRepository ticketRepository;

    public RelatorioDesempenhoDTO gerarRelatorio(RelatorioFiltroDTO filtro) {
        	List<Ticket> tickets = ticketRepository.buscarPorFiltros(filtro.getDataInicio(), filtro.getDataFim(), filtro.getAgenteId(), filtro.getFilaId());
        
        RelatorioDesempenhoDTO relatorio = new RelatorioDesempenhoDTO();
        int totalTickets = tickets.size();
        int ticketsCumpridosSLA = 0;
        int ticketsNaoCumpridosSLA = 0;
        long somaTempoResolucao = 0;

        for (Ticket ticket : tickets) {
            if (ticket.getSlaStatus() == SlaStatus.DENTRO_DO_PRAZO) {
                ticketsCumpridosSLA++;
            } else {
                ticketsNaoCumpridosSLA++;
            }

            Duration duracao = Duration.between(ticket.getDataCriacao(), ticket.getPrazoFinal());
            somaTempoResolucao += duracao.toMinutes();
        }

        relatorio.setTotalTickets(totalTickets);
        relatorio.setTicketsCumpridosSLA(ticketsCumpridosSLA);
        relatorio.setTicketsNaoCumpridosSLA(ticketsNaoCumpridosSLA);
        relatorio.setTempoMedioResolucao(totalTickets > 0 ? somaTempoResolucao / totalTickets : 0);

        return relatorio;
    }
}
