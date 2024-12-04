package com.chamadas.TicketSystem.service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.enums.Status;
import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.repository.TicketRepository;

@Service
//Serviço responsável pelo gerenciamento de escalonamentos e notificações relacionados ao SLA.
public class EscalonamentoService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketHistoricoService ticketHistoricoService;

    @Autowired
    private JavaMailSender javaMailSender;  // Injeção do JavaMailSender para enviar e-mails via Zoho Mail

    // Executa a cada 1 minuto para monitorar os SLA dos tickets
    @Scheduled(fixedRate = 60000)
    public void monitorarSlaTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Ticket ticket : tickets) {
            if (ticket.getStatus() == Status.ABERTO) {
                long minutesToDeadline = Duration.between(now, ticket.getPrazoFinal()).toMinutes();

                if (minutesToDeadline <= 15 && minutesToDeadline > 0) {
                    ticket.setSlaStatus(SlaStatus.PERTO_DO_VENCIMENTO);
                    ticketRepository.save(ticket);
                    enviarAlertaSla(ticket); // Alerta para prazo próximo do vencimento

                } else if (minutesToDeadline <= 0) {
                    ticket.setSlaStatus(SlaStatus.VENCIDO);
                    ticketRepository.save(ticket);
                    enviarAlertaSla(ticket); // Alerta para prazo vencido

                } else {
                    // Caso o ticket esteja dentro do prazo
                    ticket.setSlaStatus(SlaStatus.DENTRO_DO_PRAZO);
                    ticketRepository.save(ticket);
                }
            }
        }
    }

    // Método para enviar alertas de SLA utilizando Zoho Mail (JavaMailSender)
    private void enviarAlertaSla(Ticket ticket) {
        String subject = "Alerta de SLA: " + ticket.getTitulo();
        String content = "O ticket " + ticket.getTitulo() + " está " + ticket.getSlaStatus() + ". Por favor, verifique o atendimento.";

        // Enviar o e-mail para o cliente (ou agente)
        enviarEmail(ticket.getCliente().getEmail(), subject, content);
    }

    // Método para escalonar tickets com prazo vencido
    @Scheduled(fixedRate = 60000) // Executa a cada 1 minuto, mas pode ser ajustado conforme necessidade
    public void escalonarTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        for (Ticket ticket : tickets) {
            // Verifica se o ticket está ABERTO e o prazo final passou
            if (ticket.getStatus() == Status.ABERTO && 
                ticket.getPrazoFinal().isBefore(LocalDateTime.now())) {

                // Escalonar o ticket (Ex: mudar de fila ou notificar responsáveis)
                ticket.setStatus(Status.ESCALADO);
                ticketRepository.save(ticket);

                // Registrar ação no histórico do ticket
                String mensagem = "O status do ticket #" + ticket.getId() + " foi alterado para: " + ticket.getStatus();
                ticketHistoricoService.registrarAcao(ticket, mensagem);

                // Notificar por e-mail que o ticket foi escalonado
                enviarNotificacaoEscalonamento(ticket);
            }
        }
    }

    // Método para enviar notificação de escalonamento por e-mail utilizando Zoho Mail (JavaMailSender)
    private void enviarNotificacaoEscalonamento(Ticket ticket) {
        String subject = "Ticket Escalonado: " + ticket.getTitulo();
        String content = "O ticket " + ticket.getTitulo() + " foi escalonado devido ao prazo excedido.";

        // Enviar o e-mail para o agente responsável
        enviarEmail(ticket.getAgente().getEmail(), subject, content);
    }

    // Método genérico para envio de e-mails utilizando JavaMailSender (Zoho Mail)
    private void enviarEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aluno.estagiario7@gmail.com");  // Coloque seu e-mail do Zoho
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        // Envia o e-mail
        javaMailSender.send(message);
    }
}
