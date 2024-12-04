package com.chamadas.TicketSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.model.Agente;
import com.chamadas.TicketSystem.repository.AgenteRepository;

@Service
//Serviço que gerencia agentes, incluindo criação, listagem, atualização e remoção.
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    public Agente criarAgente(Agente agente) {
        return agenteRepository.save(agente);
    }

    public List<Agente> listarAgentes() {
        return agenteRepository.findAll();
    }

    public Agente buscarPorId(Long id) {
        return agenteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Agente não encontrado"));
    }

    public Agente atualizarAgente(Long id, Agente agenteAtualizado) {
        Agente agente = buscarPorId(id);
        agente.setNome(agenteAtualizado.getNome());
        agente.setEmail(agenteAtualizado.getEmail());
        return agenteRepository.save(agente);
    }

    public void deletarAgente(Long id) {
        Agente agente = buscarPorId(id);
        agenteRepository.delete(agente);
    }
}
