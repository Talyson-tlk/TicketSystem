package com.chamadas.TicketSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chamadas.TicketSystem.dto.RelatorioDesempenhoDTO;
import com.chamadas.TicketSystem.dto.RelatorioFiltroDTO;
import com.chamadas.TicketSystem.service.RelatorioService;


@RestController
@RequestMapping("/api/relatorios")
//Controlador para geração de relatórios de desempenho.
//Permite criar relatórios filtrados por critérios como datas e IDs de agente ou fila.
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping("/desempenho")
    public ResponseEntity<RelatorioDesempenhoDTO> gerarRelatorioDesempenho(@RequestBody RelatorioFiltroDTO filtroDTO) {
        RelatorioDesempenhoDTO relatorio = relatorioService.gerarRelatorio(filtroDTO);
        return ResponseEntity.ok(relatorio);
    }
}
