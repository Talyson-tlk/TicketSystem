package com.chamadas.TicketSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.dto.FilaDTO;
import com.chamadas.TicketSystem.model.Fila;
import com.chamadas.TicketSystem.repository.FilaRepository;


@Service
//Serviço que gerencia filas de atendimento.
public class FilaService {
    @Autowired
    private FilaRepository filaRepository;

    public Fila criarFila(FilaDTO filaDTO) {
        Fila fila = new Fila();
        fila.setNome(filaDTO.getNome());
        fila.setDescricao(filaDTO.getDescricao());
        return filaRepository.save(fila);
    }

    public List<Fila> listarFilas() {
        return filaRepository.findAll();
    }

    public Fila buscarPorId(Long id) {
        return filaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fila não encontrada"));
    }

    public Fila atualizarFila(Long id, FilaDTO filaDTO) {
        Fila fila = buscarPorId(id);
        fila.setNome(filaDTO.getNome());
        fila.setDescricao(filaDTO.getDescricao());
        return filaRepository.save(fila);
    }

    public void deletarFila(Long id) {
        Fila fila = buscarPorId(id);
        filaRepository.delete(fila);
    }
}
