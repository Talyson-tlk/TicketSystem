package com.chamadas.TicketSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamadas.TicketSystem.dto.FilaDTO;
import com.chamadas.TicketSystem.model.Fila;
import com.chamadas.TicketSystem.service.FilaService;
import com.chamadas.TicketSystem.views.Views;
import com.chamadas.TicketSystem.views.ViewsFila;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/filas")
//Controlador para gerenciar filas de atendimento.
//Permite criar, listar, buscar por ID, atualizar e deletar filas.
public class FilaController {

    @Autowired
    private FilaService filaService;

    @PostMapping
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<Fila> criarFila(@RequestBody FilaDTO filaDTO) {
        Fila fila = filaService.criarFila(filaDTO);
        return ResponseEntity.ok(fila);
    }

    @GetMapping
    @JsonView(Views.IdOnly.class)
    public ResponseEntity<List<Fila>> listarFilas() {
        return ResponseEntity.ok(filaService.listarFilas());
    }

    @GetMapping("/{id}")
    @JsonView(ViewsFila.FilaOnly.class)
    public ResponseEntity<Fila> buscarPorId(@PathVariable Long id) {
        Fila fila = filaService.buscarPorId(id);
        return ResponseEntity.ok(fila);
    }

    

    @PutMapping("/{id}")
    @JsonView(ViewsFila.FilaOnly.class)
    public ResponseEntity<Fila> atualizarFila(@PathVariable Long id, @RequestBody FilaDTO filaDTO) {
        Fila fila = filaService.atualizarFila(id, filaDTO);
        return ResponseEntity.ok(fila);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFila(@PathVariable Long id) {
        filaService.deletarFila(id);
        return ResponseEntity.noContent().build();
    }
}
