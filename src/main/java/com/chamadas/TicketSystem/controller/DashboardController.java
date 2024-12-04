package com.chamadas.TicketSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamadas.TicketSystem.dto.DashboardTendenciasDTO;
import com.chamadas.TicketSystem.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
//Controlador que fornece dados para o dashboard de tendências.
//Exibe métricas sobre abertura e fechamento de chamados agrupados por mês.
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/tendencias")
    public ResponseEntity<DashboardTendenciasDTO> obterDadosTendencias() {
        DashboardTendenciasDTO dashboardDTO = dashboardService.obterDadosTendencias();
        return ResponseEntity.ok(dashboardDTO);
    }
}
