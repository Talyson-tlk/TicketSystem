package com.chamadas.TicketSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chamadas.TicketSystem.model.Agente;
import com.chamadas.TicketSystem.service.AgenteService;
import com.chamadas.TicketSystem.views.ViewsAgente;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/agentes")
//Controlador para operações relacionadas a agentes.
//Permite criar, listar, buscar por ID, atualizar e deletar agentes.
public class AgenteController {

    @Autowired
    private AgenteService agenteService;

    @PostMapping
    public ResponseEntity<Agente> criarAgente(@RequestBody Agente agente) {
        return ResponseEntity.ok(agenteService.criarAgente(agente));
    }

    @GetMapping
	@JsonView(ViewsAgente.AgenteOnly.class)
    public ResponseEntity<List<Agente>> listarAgentes() {
        return ResponseEntity.ok(agenteService.listarAgentes());
    }

    @GetMapping("/{id}")
	@JsonView(ViewsAgente.AgenteOnly.class)
    public ResponseEntity<Agente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agenteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
	@JsonView(ViewsAgente.AgenteOnly.class)
    public ResponseEntity<Agente> atualizarAgente(@PathVariable Long id, @RequestBody Agente agente) {
        return ResponseEntity.ok(agenteService.atualizarAgente(id, agente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgente(@PathVariable Long id) {
        agenteService.deletarAgente(id);
        return ResponseEntity.noContent().build();
    }
}
