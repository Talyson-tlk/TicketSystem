package com.chamadas.TicketSystem.service;

import com.chamadas.TicketSystem.dto.DashboardTendenciasDTO;
import com.chamadas.TicketSystem.enums.Status;
import com.chamadas.TicketSystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//Serviço para gerar e processar dados exibidos no dashboard.
public class DashboardService {

    @Autowired
    private TicketRepository ticketRepository;

    public DashboardTendenciasDTO obterDadosTendencias() {
        DashboardTendenciasDTO dashboardDTO = new DashboardTendenciasDTO();

        dashboardDTO.setTicketsAbertosPorMes(obterVolumeTicketsPorMes(Status.ABERTO));
        dashboardDTO.setTicketsFechadosComSucessoPorMes(obterVolumeTicketsPorMes(Status.FECHADO_COM_SUCESSO));
        dashboardDTO.setTicketsFechadosSemSucessoPorMes(obterVolumeTicketsPorMes(Status.FECHADO_SEM_SUCESSO));

        return dashboardDTO;
    }

    private Map<String, Long> obterVolumeTicketsPorMes(Status status) {
        Map<String, Long> volumePorMes = new HashMap<>();

        List<Object[]> resultados = ticketRepository.contarTicketsPorMesEStatus(status);
        for (Object[] resultado : resultados) {
            int ano = (Integer) resultado[0];
            int mes = (Integer) resultado[1];
            long contagem = (Long) resultado[2];

            YearMonth mesAno = YearMonth.of(ano, mes);
            volumePorMes.put(mesAno.toString(), contagem);
        }

        return volumePorMes;
    }
}