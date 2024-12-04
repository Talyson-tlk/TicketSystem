package com.chamadas.TicketSystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.dto.NotaDTO;
import com.chamadas.TicketSystem.model.Nota;
import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.repository.NotaRepository;
import com.chamadas.TicketSystem.repository.TicketRepository;

@Service
//Serviço para gerenciamento de notas associadas a tickets.
public class NotaService {
    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Nota criarNota(NotaDTO notaDTO) {
        if (notaDTO.getTicketId() == null) {
            throw new IllegalArgumentException("O ID do ticket não pode ser nulo");
        }

        Ticket ticket = ticketRepository.findById(notaDTO.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        Nota nota = new Nota();
        nota.setTipoNota(notaDTO.getTipoNota());
        nota.setConteudo(notaDTO.getConteudo());
        nota.setTicket(ticket);
        nota.setDataCriacao(LocalDateTime.now());

        return notaRepository.save(nota);
    }


    public List<Nota> listarNotasPorTicket(Long ticketId) {
        return notaRepository.findByTicketId(ticketId);
    }

    public Nota buscarPorId(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));
    }

    public Nota atualizarNota(Long id, NotaDTO notaDTO) {
        Nota nota = buscarPorId(id);
        nota.setConteudo(notaDTO.getConteudo());
        nota.setTipoNota(notaDTO.getTipoNota());
        return notaRepository.save(nota);
    }

    public void deletarNota(Long id) {
        Nota nota = buscarPorId(id);
        notaRepository.delete(nota);
    }
}


