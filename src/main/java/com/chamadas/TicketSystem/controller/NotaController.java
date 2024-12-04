package com.chamadas.TicketSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chamadas.TicketSystem.dto.NotaDTO;
import com.chamadas.TicketSystem.model.Nota;
import com.chamadas.TicketSystem.service.NotaService;
import com.chamadas.TicketSystem.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/notas")
//Controlador para gerenciar notas associadas aos tickets.
//Permite criar, listar por ticket, buscar por ID, atualizar e deletar notas.
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Nota> criarNota(@RequestBody NotaDTO notaDTO) {
        Nota nota = notaService.criarNota(notaDTO);
        return ResponseEntity.ok(nota);
    }

    @GetMapping("/ticket/{ticketId}")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<List<Nota>> listarNotasPorTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(notaService.listarNotasPorTicket(ticketId));
    }

    @GetMapping("/{id}")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Nota> buscarPorId(@PathVariable Long id) {
        Nota nota = notaService.buscarPorId(id);
        return ResponseEntity.ok(nota);
    }

    @PutMapping("/{id}")
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Nota> atualizarNota(@PathVariable Long id, @RequestBody NotaDTO notaDTO) {
        Nota nota = notaService.atualizarNota(id, notaDTO);
        return ResponseEntity.ok(nota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNota(@PathVariable Long id) {
        notaService.deletarNota(id);
        return ResponseEntity.noContent().build();
    }
}
