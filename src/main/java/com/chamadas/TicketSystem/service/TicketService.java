package com.chamadas.TicketSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.dto.TicketDTO;
import com.chamadas.TicketSystem.dto.TicketFiltroDTO;
import com.chamadas.TicketSystem.enums.Criticidade;
import com.chamadas.TicketSystem.enums.Impacto;
import com.chamadas.TicketSystem.enums.Prioridade;
import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.enums.Status;
import com.chamadas.TicketSystem.model.Agente;
import com.chamadas.TicketSystem.model.Cliente;
import com.chamadas.TicketSystem.model.Feriado;
import com.chamadas.TicketSystem.model.Fila;
import com.chamadas.TicketSystem.model.HorarioAtendimento;
import com.chamadas.TicketSystem.model.SLA;
import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.repository.AgenteRepository;
import com.chamadas.TicketSystem.repository.ClienteRepository;
import com.chamadas.TicketSystem.repository.FeriadoRepository;
import com.chamadas.TicketSystem.repository.FilaRepository;
import com.chamadas.TicketSystem.repository.HorarioAtendimentoRepository;
import com.chamadas.TicketSystem.repository.TicketRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
//Serviço para gerenciamento completo de tickets, incluindo ajuste de prioridade e integração com SLAs.
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FilaRepository filaRepository;

    @Autowired
    private SLAService slaService;
    
    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private AgenteRepository agenteRepository;
        
    @Autowired
    private TicketHistoricoService ticketHistoricoService;
    
    @Autowired
    private FeriadoRepository feriadoRepository;
    
    @Autowired
    private HorarioAtendimentoRepository horarioAtendimentoRepository;
    
  // Ajusta a prioridade de um ticket com base no status do SLA.
    public void ajustarPrioridadePorSLA(Ticket ticket) {
        if (ticket.getSlaStatus() == SlaStatus.PERTO_DO_VENCIMENTO && ticket.getPrioridade() != Prioridade.ALTA) {
            ticket.setPrioridade(Prioridade.ALTA);
            notificacaoService.enviarNotificacaoPorEmail(
            	    ticket.getAgente(),
            	    String.format("Atenção: O ticket '%s' (ID: %d) está perto do vencimento de sua SLA", 
            	                  ticket.getTitulo(), 
            	                  ticket.getId()),
            	    ticket
            	);

        } else if (ticket.getSlaStatus() == SlaStatus.VENCIDO && ticket.getPrioridade() != Prioridade.ALTÍSSIMA) {
            ticket.setPrioridade(Prioridade.ALTÍSSIMA);
            notificacaoService.enviarNotificacaoPorEmail(
            	    ticket.getAgente(),
            	    String.format("Atenção: Devido ao vencimento do SLA, o ticket '%s' (ID: %d) está em prioridade altíssima", 
            	                  ticket.getTitulo(), 
            	                  ticket.getId()),
            	    ticket
            	);
        }

        ticketRepository.save(ticket);
    }
    
    

    /**
     * Ajusta o prazo de SLA para considerar feriados e horários de atendimento.
     * 
     * O método verifica se o prazo original respeita os horários de atendimento e se não
     * coincide com feriados. Caso contrário, ajusta o prazo iterativamente até encontrar
     * um horário válido, adicionando 1 hora por vez e, se necessário, avançando para o próximo
     * dia útil ao fim do expediente.
     * 
     */
    public LocalDateTime ajustarSLAParaHorarios(LocalDateTime prazoOriginal) {
        List<Feriado> feriados = feriadoRepository.findAll();
        List<HorarioAtendimento> horarios = horarioAtendimentoRepository.findAll();

        while (!isHorarioValido(prazoOriginal, feriados, horarios)) {
            prazoOriginal = prazoOriginal.plusHours(1);

            // Se o horário passar do expediente, ajuste para o próximo dia útil
            if (prazoOriginal.toLocalTime().isAfter(LocalTime.of(18, 0))) { // Exemplo: fim do expediente
                prazoOriginal = prazoOriginal.plusDays(1).with(LocalTime.of(8, 0)); // Próximo dia útil às 8h
            }
        }

        return prazoOriginal;
    }



    /**
     * Verifica se uma data e hora específica está dentro de um horário válido.
     * 
     * Regras:
     * - O horário é considerado válido se:
     *   1. Não for um feriado.
     *   2. Estiver dentro do horário de atendimento em um dia útil.
     */
    private boolean isHorarioValido(LocalDateTime dataHora, List<Feriado> feriados, List<HorarioAtendimento> horarios) {
        // Verifica se a data corresponde a um feriado cadastrado.
        boolean ehFeriado = feriados.stream()
                                    .anyMatch(feriado -> feriado.getData().equals(dataHora.toLocalDate()));

        // Obtém o dia da semana da dataHora (ex.: "MONDAY", "TUESDAY").
        String diaSemanaAtual = dataHora.getDayOfWeek().toString();

        // Verifica se o horário está dentro do expediente para o dia da semana correspondente.
        boolean dentroHorarioAtendimento = horarios.stream()
                .anyMatch(horario -> mapearDiaSemana(horario.getDiaSemana()).equalsIgnoreCase(diaSemanaAtual)
                    && dataHora.toLocalTime().isAfter(LocalTime.parse(horario.getHoraInicio()))
                    && dataHora.toLocalTime().isBefore(LocalTime.parse(horario.getHoraFim())));

        // O horário é válido se não for feriado e estiver dentro do horário de atendimento.
        return !ehFeriado && dentroHorarioAtendimento;
    }


    /**
     * Mapeia um dia da semana em português para sua representação em inglês.
     * 
     * Este método é usado para converter nomes de dias da semana em português (ex.: "SEGUNDA")
     * para os correspondentes em inglês (ex.: "MONDAY"), facilitando a comparação com as datas
     * em um formato de enumeração em inglês.
     */
    private String mapearDiaSemana(String diaSemana) {
        Map<String, String> diasSemana = new HashMap<>();
        diasSemana.put("SEGUNDA", "MONDAY");
        diasSemana.put("TERÇA", "TUESDAY");
        diasSemana.put("QUARTA", "WEDNESDAY");
        diasSemana.put("QUINTA", "THURSDAY");
        diasSemana.put("SEXTA", "FRIDAY");
        diasSemana.put("SÁBADO", "SATURDAY");
        diasSemana.put("DOMINGO", "SUNDAY");

        return diasSemana.getOrDefault(diaSemana.toUpperCase(), diaSemana);
    }


    
    @Scheduled(cron = "0 0 0 * * ?") // Executa todos os dias à meia-noite
    public void fecharTicketsInativos() {
        LocalDateTime limiteInatividade = LocalDateTime.now().minusDays(30); // Definindo 30 dias de inatividade
        List<Ticket> ticketsInativos = ticketRepository.findTicketsInativos(limiteInatividade, Status.ABERTO);

        for (Ticket ticket : ticketsInativos) {
            ticket.setStatus(Status.FECHADO_SEM_SUCESSO);
            ticketRepository.save(ticket);

            ticketHistoricoService.registrarAcao(ticket, "Ticket fechado automaticamente por inatividade");
        }
    }
    
    public void atualizarDataUltimaAtividade(Ticket ticket) {
        ticket.setDataUltimaAtividade(LocalDateTime.now());
        ticketRepository.save(ticket);
    }
    

 
    public List<Ticket> buscarTicketsComFiltros(TicketFiltroDTO filtroDTO) {
        return ticketRepository.buscarComFiltros(
                filtroDTO.getStatus(),
                filtroDTO.getPrioridade(),
                filtroDTO.getClienteId(),
                filtroDTO.getAgenteId(),
                filtroDTO.getDataCriacaoInicio(),
                filtroDTO.getDataCriacaoFim()
        );
    }
    
    
    public void definirPrioridadeDinamica(Ticket ticket) {
        if (ticket.getImpacto() == Impacto.MUITO_ALTO && ticket.getCriticidade() == Criticidade.MUITO_CRITICO) {
            ticket.setPrioridade(Prioridade.ALTA);
        } else if (ticket.getImpacto() == Impacto.ALTO || ticket.getCriticidade() == Criticidade.CRITICO) {
            ticket.setPrioridade(Prioridade.MEDIA);
        } else {
            ticket.setPrioridade(Prioridade.BAIXA);
        }
    }

    
    
    @Scheduled(fixedRate = 60000) // Verifica SLAs a cada minuto
    public void verificarSLAs() {
        List<Ticket> tickets = ticketRepository.findAll();
        for (Ticket ticket : tickets) {
            if (ticket.getPrazoFinal().isBefore(LocalDateTime.now())) {
                ticket.setSlaStatus(SlaStatus.VENCIDO);
            } else if (ticket.getPrazoFinal().minusHours(2).isBefore(LocalDateTime.now())) {
                ticket.setSlaStatus(SlaStatus.PERTO_DO_VENCIMENTO);
            } else {
                ticket.setSlaStatus(SlaStatus.DENTRO_DO_PRAZO);
            }

            ajustarPrioridadePorSLA(ticket); // Ajusta a prioridade com base no SLA
            ticketRepository.save(ticket);
        }
    }


    

    public Ticket criarTicket(TicketDTO ticketDTO) {
        Cliente cliente = clienteRepository.findById(ticketDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Fila fila = filaRepository.findById(ticketDTO.getFilaId())
                .orElseThrow(() -> new RuntimeException("Fila não encontrada"));

        Agente agente = agenteRepository.findById(ticketDTO.getAgenteId())
                .orElseThrow(() -> new RuntimeException("Agente não encontrado"));

        Ticket ticket = new Ticket();
        ticket.setTitulo(ticketDTO.getTitulo());
        ticket.setDescricao(ticketDTO.getDescricao());
        ticket.setStatus(Status.NOVO);
        ticket.setDataCriacao(LocalDateTime.now());
        ticket.setCliente(cliente);
        ticket.setFila(fila);
        ticket.setAgente(agente);
        ticket.setImpacto(ticketDTO.getImpacto());
        ticket.setCriticidade(ticketDTO.getCriticidade());

        definirPrioridadeDinamica(ticket);

        // Configurar o prazo com base no SLA
        SLA sla = slaService.buscarPorFilaId(fila.getId())
                .orElseThrow(() -> new RuntimeException("SLA não encontrado para a fila"));
        LocalDateTime prazoOriginal = LocalDateTime.now().plus(sla.getTempoResolucao());
        ticket.setPrazoFinal(ajustarSLAParaHorarios(prazoOriginal));

        ticketRepository.save(ticket);

        // Enviar notificação ao cliente e ao agente
        notificacaoService.enviarNotificacaoPorEmail(cliente, "Seu ticket foi criado com sucesso!", ticket);
        notificacaoService.enviarNotificacaoPorEmail(agente, "Você foi atribuído a um novo ticket.", ticket);
       
        return ticket;
    }


    
    
    public Map<String, List<Ticket>> gerarRelatorioSla() {
        List<Ticket> dentroDoPrazo = ticketRepository.findBySlaStatus(SlaStatus.DENTRO_DO_PRAZO);
        List<Ticket> pertoDoVencimento = ticketRepository.findBySlaStatus(SlaStatus.PERTO_DO_VENCIMENTO);
        List<Ticket> vencido = ticketRepository.findBySlaStatus(SlaStatus.VENCIDO);

        Map<String, List<Ticket>> relatorio = new HashMap<>();
        relatorio.put("Dentro do Prazo", dentroDoPrazo);
        relatorio.put("Perto do Vencimento", pertoDoVencimento);
        relatorio.put("Vencido", vencido);

        return relatorio;
    }

    public List<Ticket> listarTickets() {
        return ticketRepository.findAll();
    }

    public Ticket buscarPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
    }

    public Ticket atualizarTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = buscarPorId(id);
        
        String statusAntigo = ticket.getStatus().toString(); // Converte o Status para String

        
        ticket.setTitulo(ticketDTO.getTitulo());
        ticket.setDescricao(ticketDTO.getDescricao());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setImpacto(ticketDTO.getImpacto());
        ticket.setCriticidade(ticketDTO.getCriticidade());
        definirPrioridadeDinamica(ticket);
        ticketRepository.save(ticket);

        if (!statusAntigo.equals(ticketDTO.getStatus().toString())) {
            String mensagem = "O status do ticket #" + ticket.getId() + " foi alterado para: " + ticketDTO.getStatus();
            
            notificacaoService.enviarNotificacaoInterna(ticket.getCliente(), mensagem, ticket);
      
            ticketHistoricoService.registrarAcao(ticket, mensagem);
        } else {
            ticketHistoricoService.registrarAcao(ticket, "Ticket atualizado");
        }
        atualizarDataUltimaAtividade(ticket);
        return ticket;
    }

    public void deletarTicket(Long id) {
        Ticket ticket = buscarPorId(id);
        ticketRepository.delete(ticket);
    }
    
    private Ticket buscarId(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado."));
    }
    
    public Ticket fecharTicket(Long ticketId, Status statusFechamento, String motivoFechamento) {
        Ticket ticket = buscarId(ticketId);

        if (!ticket.getStatus().equals(Status.ABERTO) 
        		&& !ticket.getStatus().equals(Status.REABERTO) 
        		&& !ticket.getStatus().equals(Status.ESCALADO)) {
            throw new RuntimeException("Apenas tickets abertos, reabertos ou escalados podem ser fechados.");
        }

        ticket.setStatus(statusFechamento);
        ticket.setDataUltimaAtividade(LocalDateTime.now());
        ticketRepository.save(ticket);

        // Registrar no histórico
        String descricaoHistorico = "Ticket fechado com status: " + statusFechamento + ". Motivo: " + motivoFechamento;
        ticketHistoricoService.registrarAcao(ticket, descricaoHistorico);

        return ticket;
    }
    
    public Ticket reabrirTicket(Long ticketId, String motivoReabertura) {
        Ticket ticket = buscarId(ticketId);

        if (!ticket.getStatus().equals(Status.FECHADO_COM_SUCESSO) 
        		&& !ticket.getStatus().equals(Status.FECHADO_SEM_SUCESSO)) {
            throw new RuntimeException("Apenas tickets fechados podem ser reabertos.");
        }

        ticket.setStatus(Status.REABERTO);
        ticket.setDataUltimaAtividade(LocalDateTime.now());
        ticketRepository.save(ticket);

        // Registrar reabertura no histórico
        ticketHistoricoService.registrarAcao(ticket, "Ticket reaberto. Motivo: " + motivoReabertura);

        return ticket;
    }
    
    
}
