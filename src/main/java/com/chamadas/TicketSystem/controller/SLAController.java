package com.chamadas.TicketSystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chamadas.TicketSystem.dto.SLADTO;
import com.chamadas.TicketSystem.model.SLA;
import com.chamadas.TicketSystem.service.SLAService;
import com.chamadas.TicketSystem.views.Views;
import com.chamadas.TicketSystem.views.ViewsSLA;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/slas")
// Controlador para gerenciar SLAs.
//Permite criar, listar, buscar por ID ou por fila, atualizar e deletar SLAs.
public class SLAController {

    @Autowired
    private SLAService slaService;

    @PostMapping
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<SLA> criarSLA(@RequestBody SLADTO SLADTO) {
        SLA sla = slaService.criarSLA(SLADTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(sla);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SLA> buscarPorId(@PathVariable Long id) {
        return slaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fila/{filaId}")
    public ResponseEntity<SLA> buscarPorFilaId(@PathVariable Long filaId) {
        return slaService.buscarPorFilaId(filaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
	@JsonView(ViewsSLA.SLAOnly.class)
    public ResponseEntity<List<SLA>> listarTodos() {
        return ResponseEntity.ok(slaService.listarTodos());
    }

    @PutMapping("/{id}")
	@JsonView(ViewsSLA.SLAOnly.class)
    public ResponseEntity<SLA> atualizarSLA(@PathVariable Long id, @RequestBody SLADTO slaDto) {
        try {
            SLA slaAtualizado = slaService.atualizarSLA(id, slaDto.toEntity());
            return ResponseEntity.ok(slaAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSLA(@PathVariable Long id) {
        slaService.deletarSLA(id);
        return ResponseEntity.noContent().build();
    }
}
