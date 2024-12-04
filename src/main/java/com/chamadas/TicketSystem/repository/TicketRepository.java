package com.chamadas.TicketSystem.repository;

import com.chamadas.TicketSystem.enums.Prioridade;
import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.enums.Status;
import com.chamadas.TicketSystem.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//Repositório JPA para gerenciar tickets.

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByClienteId(Long cliente_id);

	List<Ticket> findBySlaStatus(SlaStatus dentroDoPrazo);
	
	  
    @Query("SELECT t FROM Ticket t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:prioridade IS NULL OR t.prioridade = :prioridade) AND " +
           "(:clienteId IS NULL OR t.cliente.id = :clienteId) AND " +
           "(:agenteId IS NULL OR t.agente.id = :agenteId) AND " +
           "(:dataCriacaoInicio IS NULL OR t.dataCriacao >= :dataCriacaoInicio) AND " +
           "(:dataCriacaoFim IS NULL OR t.dataCriacao <= :dataCriacaoFim)")
    List<Ticket> buscarComFiltros(
            @Param("status") Status status,
            @Param("prioridade") Prioridade prioridade,
            @Param("clienteId") Long clienteId,
            @Param("agenteId") Long agenteId,
            @Param("dataCriacaoInicio") LocalDateTime dataCriacaoInicio,
            @Param("dataCriacaoFim") LocalDateTime dataCriacaoFim);
    
    
    @Query("SELECT t FROM Ticket t WHERE " +
    	       "(:dataInicio IS NULL OR t.dataCriacao >= :dataInicio) AND " +
    	       "(:dataFim IS NULL OR t.dataCriacao <= :dataFim) AND " +
    	       "(:agenteId IS NULL OR t.agente.id = :agenteId) AND " +
    	       "(:filaId IS NULL OR t.fila.id = :filaId)")
    	List<Ticket> buscarPorFiltros(@Param("dataInicio") LocalDateTime dataInicio,
    	                              @Param("dataFim") LocalDateTime dataFim,
    	                              @Param("agenteId") Long agenteId,
    	                              @Param("filaId") Long filaId);
    
    int countByStatus(Status status);
    int countBySlaStatus(SlaStatus slaStatus);
    
    @Query("SELECT t FROM Ticket t WHERE t.dataUltimaAtividade < :limiteInatividade AND t.status = :status")
    List<Ticket> findTicketsInativos(@Param("limiteInatividade") LocalDateTime limiteInatividade, @Param("status") Status status);

    @Query("SELECT YEAR(t.dataCriacao) AS ano, MONTH(t.dataCriacao) AS mes, COUNT(t) " +
    	       "FROM Ticket t WHERE t.status = :status " +
    	       "GROUP BY YEAR(t.dataCriacao), MONTH(t.dataCriacao)")
    	List<Object[]> contarTicketsPorMesEStatus(@Param("status") Status status);
}

