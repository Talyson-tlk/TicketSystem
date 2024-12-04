package com.chamadas.TicketSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.model.SLA;
import com.chamadas.TicketSystem.repository.SLARepository;

import jakarta.persistence.EntityNotFoundException;

@Service
//Serviço para criação e gerenciamento de SLAs.
public class SLAService {

    @Autowired
    private SLARepository slaRepository;

    public SLA criarSLA(SLA sla) {
        return slaRepository.save(sla);
    }

    public Optional<SLA> buscarPorId(Long id) {
        return slaRepository.findById(id);
    }

    public Optional<SLA> buscarPorFilaId(Long filaId) {
        return slaRepository.findByFilaId(filaId);
    }

    public List<SLA> listarTodos() {
        return slaRepository.findAll();
    }

    public SLA atualizarSLA(Long id, SLA slaAtualizado) {
        return slaRepository.findById(id)
                .map(sla -> {
                    sla.setFila(slaAtualizado.getFila());
                    sla.setTempoResposta(slaAtualizado.getTempoResposta());
                    sla.setTempoResolucao(slaAtualizado.getTempoResolucao());
                    return slaRepository.save(sla);
                })
                .orElseThrow(() -> new EntityNotFoundException("SLA não encontrado"));
    }

    public void deletarSLA(Long id) {
        slaRepository.deleteById(id);
    }
}
