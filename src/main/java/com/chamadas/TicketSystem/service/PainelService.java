package com.chamadas.TicketSystem.service;

import com.chamadas.TicketSystem.dto.PainelDTO;
import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.enums.Status;
import com.chamadas.TicketSystem.repository.TicketRepository;
import com.chamadas.TicketSystem.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//Serviço para gerar dados exibidos no painel de controle.
public class PainelService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public PainelDTO obterDadosPainel() {
        PainelDTO painelDTO = new PainelDTO();

        painelDTO.setTotalTicketsAbertos(ticketRepository.countByStatus(Status.ABERTO));
        painelDTO.setTicketsVencendoSLA(ticketRepository.countBySlaStatus(SlaStatus.PERTO_DO_VENCIMENTO));
        painelDTO.setTicketsVencidosSLA(ticketRepository.countBySlaStatus(SlaStatus.VENCIDO));
        painelDTO.setNotificacoesNaoVisualizadas(notificacaoRepository.countByVisualizadaFalse());
        return painelDTO;
    }
}
