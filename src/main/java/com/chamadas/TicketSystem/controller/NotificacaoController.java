package com.chamadas.TicketSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamadas.TicketSystem.model.Notificacao;
import com.chamadas.TicketSystem.service.NotificacaoService;
import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/notificacoes")
// Controlador para gerenciar notificações.
//Suporta listar notificações não visualizadas e marcar notificações como visualizadas.
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/nao-visualizadas/{clienteId}")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<List<Notificacao>> listarNaoVisualizadas(@PathVariable Long clienteId) {
        List<Notificacao> notificacoes = notificacaoService.listarNotificacoesNaoVisualizadas(clienteId);
        return ResponseEntity.ok(notificacoes);
    }

    @PutMapping("/visualizar/{notificacaoId}")
    public ResponseEntity<Void> marcarComoVisualizada(@PathVariable Long notificacaoId) {
        notificacaoService.marcarComoVisualizada(notificacaoId);
        return ResponseEntity.noContent().build();
    }
}
