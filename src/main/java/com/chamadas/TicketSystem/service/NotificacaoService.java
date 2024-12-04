package com.chamadas.TicketSystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chamadas.TicketSystem.enums.SlaStatus;
import com.chamadas.TicketSystem.model.Agente;
import com.chamadas.TicketSystem.model.Cliente;
import com.chamadas.TicketSystem.model.Notificacao;
import com.chamadas.TicketSystem.model.Ticket;
import com.chamadas.TicketSystem.repository.NotificacaoRepository;

@Service
//Serviço para envio e controle de notificações.
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private JavaMailSender javaMailSender; // Usando JavaMailSender para enviar e-mails via Zoho Mail

    // Método para verificar SLA e enviar notificações
    public void verificarSLANotificacoes(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getSlaStatus() == SlaStatus.PERTO_DO_VENCIMENTO) {
                String mensagem = "O ticket #" + ticket.getId() + " está próximo do prazo de vencimento de SLA.";
                
                // Enviar para o cliente
                enviarNotificacaoInterna(ticket.getCliente(), mensagem, ticket);

                // Enviar para o agente responsável
                if (ticket.getAgente() != null) {
                    enviarNotificacaoPorEmail(ticket.getAgente(), mensagem, ticket);
                }
            }
        }
    }

    // Método para enviar notificações por e-mail (Cliente)
    public void enviarNotificacaoPorEmail(Cliente cliente, String mensagem, Ticket ticket) {
        try {
            // Enviar o e-mail para o cliente via Zoho Mail (JavaMailSender)
            enviarEmail(cliente.getEmail(), "Notificação sobre o ticket: " + ticket.getTitulo(), mensagem);
            
            // Registrar a notificação no banco
            Notificacao notificacao = new Notificacao();
            notificacao.setMensagem(mensagem);
            notificacao.setDataEnvio(LocalDateTime.now());
            notificacao.setTicket(ticket);
            notificacao.setCliente(cliente);
            notificacaoRepository.save(notificacao);

        } catch (Exception e) {
            // Log ou tratamento de erro caso o envio de e-mail falhe
            e.printStackTrace();
        }
    }

    // Método para enviar notificações por e-mail (Agente)
    public void enviarNotificacaoPorEmail(Agente agente, String mensagem, Ticket ticket) {
        try {
            // Enviar o e-mail para o agente via Zoho Mail (JavaMailSender)
            enviarEmail(agente.getEmail(), "Notificação sobre o ticket: " + ticket.getTitulo(), mensagem);

            // Registrar a notificação no banco
            Notificacao notificacao = new Notificacao();
            notificacao.setMensagem(mensagem);
            notificacao.setDataEnvio(LocalDateTime.now());
            notificacao.setTicket(ticket);
            notificacao.setAgente(agente);
            notificacaoRepository.save(notificacao);

        } catch (Exception e) {
            // Log ou tratamento de erro caso o envio de e-mail falhe
            e.printStackTrace();
        }
    }

    // Método para enviar e-mail via Zoho Mail utilizando JavaMailSender
    private void enviarEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aluno.estagiario7@gmail.com"); // Seu e-mail verificado no Zoho Mail
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        // Envia o e-mail
        javaMailSender.send(message);
    }

    // Método para enviar notificação interna no sistema
    public void enviarNotificacaoInterna(Cliente cliente, String mensagem, Ticket ticket) {
        Notificacao notificacao = new Notificacao();
        notificacao.setMensagem(mensagem);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setTicket(ticket);
        notificacao.setCliente(cliente);
        notificacao.setVisualizada(false); // Notificação ainda não foi visualizada
        notificacaoRepository.save(notificacao);
    }

    // Listar notificações não visualizadas do cliente
    public List<Notificacao> listarNotificacoesNaoVisualizadas(Long clienteId) {
        return notificacaoRepository.findByClienteIdAndVisualizadaFalse(clienteId);
    }

    // Marcar uma notificação como visualizada
    public void marcarComoVisualizada(Long notificacaoId) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
            .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacao.setVisualizada(true);
        notificacaoRepository.save(notificacao);
    }
}
