package com.chamadas.TicketSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamadas.TicketSystem.model.HorarioAtendimento;
import com.chamadas.TicketSystem.repository.HorarioAtendimentoRepository;

@RestController
@RequestMapping("/api/horarios")
// Controlador para gerenciar horários de atendimento.
//Permite criar e listar horários de funcionamento.
public class HorarioAtendimentoController {

    @Autowired
    private HorarioAtendimentoRepository horarioAtendimentoRepository;

    @PostMapping
    public ResponseEntity<HorarioAtendimento> criarHorario(@RequestBody HorarioAtendimento horario) {
        return ResponseEntity.ok(horarioAtendimentoRepository.save(horario));
    }

    @GetMapping
    public ResponseEntity<List<HorarioAtendimento>> listarHorarios() {
        return ResponseEntity.ok(horarioAtendimentoRepository.findAll());
    }
}
