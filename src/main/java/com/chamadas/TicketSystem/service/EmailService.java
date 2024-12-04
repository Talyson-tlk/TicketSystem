package com.chamadas.TicketSystem.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//Classe responsável pelo envio de e-mails no sistema.
//Utiliza o JavaMailSender para configurar e enviar mensagens de e-mail.
//Permite personalizar o destinatário, assunto e conteúdo do e-mail.
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void enviarEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aluno.estagiario7@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
