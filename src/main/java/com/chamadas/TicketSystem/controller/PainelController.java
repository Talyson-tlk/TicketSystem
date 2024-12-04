package com.chamadas.TicketSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamadas.TicketSystem.dto.PainelDTO;
import com.chamadas.TicketSystem.service.PainelService;

@RestController
@RequestMapping("/api/painel")
//Controlador para gerenciar informações do painel de controle.
//Fornece dados como total de tickets abertos e notificações não visualizadas.
public class PainelController {

    @Autowired
    private PainelService painelService;

    @GetMapping("/dados")
    public ResponseEntity<PainelDTO> obterDadosPainel() {
        PainelDTO painelDTO = painelService.obterDadosPainel();
        return ResponseEntity.ok(painelDTO);
    }
}

