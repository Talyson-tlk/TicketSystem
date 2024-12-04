package com.chamadas.TicketSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chamadas.TicketSystem.model.Notificacao;

//Repositório JPA para gerenciar notificações.

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByClienteIdAndVisualizadaFalse(Long clienteId);
    
    int countByVisualizadaFalse();

}
