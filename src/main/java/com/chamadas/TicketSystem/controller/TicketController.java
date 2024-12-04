package com.chamadas.TicketSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chamadas.TicketSystem.dto.FecharTicketDTO;
import com.chamadas.TicketSystem.dto.ReabrirTicketDTO;
import com.chamadas.TicketSystem.dto.TicketDTO;
import com.chamadas.TicketSystem.dto.TicketFiltroDTO;
import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.service.TicketService;
import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.Map;





@RestController
@RequestMapping("/api/tickets")
//Controlador para gerenciar tickets.
//Suporta criar, listar, atualizar, excluir e aplicar ações como fechar ou reabrir tickets.
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/pesquisar")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<List<Ticket>> buscarComFiltros(@RequestBody TicketFiltroDTO filtroDTO) {
        List<Ticket> tickets = ticketService.buscarTicketsComFiltros(filtroDTO);
        return ResponseEntity.ok(tickets);
    }
    
    @JsonView(Views.IdOnly.class)
    @GetMapping("/relatorio-sla")
    public ResponseEntity<Map<String, List<Ticket>>> relatorioSla() {
        Map<String, List<Ticket>> relatorio = ticketService.gerarRelatorioSla();
        return ResponseEntity.ok(relatorio);
    }

    @PostMapping
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Ticket> criarTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.criarTicket(ticketDTO);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<List<Ticket>> listarTickets() {
        return ResponseEntity.ok(ticketService.listarTickets());
    }

    @GetMapping("/{id}")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Ticket> buscarPorId(@PathVariable Long id) {
        Ticket ticket = ticketService.buscarPorId(id);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Ticket> atualizarTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.atualizarTicket(id, ticketDTO);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTicket(@PathVariable Long id) {
        ticketService.deletarTicket(id);
        return ResponseEntity.noContent().build();
    }
    
    @JsonView(Views.IdOnly.class)
    @PutMapping("/{ticketId}/fechar")
    public ResponseEntity<Ticket> fecharTicket(
            @PathVariable Long ticketId,
            @RequestBody FecharTicketDTO fecharTicketDTO) {

        Ticket ticket = ticketService.fecharTicket(ticketId, fecharTicketDTO.getStatus(), fecharTicketDTO.getMotivo());
        return ResponseEntity.ok(ticket);
    }

    @JsonView(Views.IdOnly.class)
    @PutMapping("/{ticketId}/reabrir")
    public ResponseEntity<Ticket> reabrirTicket(
            @PathVariable Long ticketId,
            @RequestBody ReabrirTicketDTO reabrirTicketDTO) {

        Ticket ticket = ticketService.reabrirTicket(ticketId, reabrirTicketDTO.getMotivo());
        return ResponseEntity.ok(ticket);
    }
}
