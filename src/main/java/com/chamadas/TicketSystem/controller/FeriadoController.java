package com.chamadas.TicketSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamadas.TicketSystem.model.Feriado;
import com.chamadas.TicketSystem.repository.FeriadoRepository;

@RestController
@RequestMapping("/api/feriados")
//Controlador para gerenciar feriados cadastrados no sistema.
//Permite criar novos feriados e listar os existentes.
public class FeriadoController {

    @Autowired
    private FeriadoRepository feriadoRepository;

    @PostMapping
    public ResponseEntity<Feriado> criarFeriado(@RequestBody Feriado feriado) {
        return ResponseEntity.ok(feriadoRepository.save(feriado));
    }

    @GetMapping
    public ResponseEntity<List<Feriado>> listarFeriados() {
        return ResponseEntity.ok(feriadoRepository.findAll());
    }
}
