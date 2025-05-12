package com.v1.apiDebt.Io.infra.adapter;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    // Injeção de Dependência
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmailConfirmacao(String emailDestino, String token) {
        String assunto = "Confirmação de Cadastro";
        String link = "Clique no link para confirmar seu cadastro: " +
                "http://localhost:8080/api/v1/auth/confirmar-cadastro?token=" + token;

        String conteudo = "Olá, \n\n" +
                "Obrigado por se cadastrar! \n\n" +
                "Para confirmar seu cadastro, clique no link abaixo: \n" +
                link + "\n\n" +
                "Atenciosamente, \n" +
                "Equipe API Debt";

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom("debit.io2025@gmail.com");
        mensagem.setTo(emailDestino);
        mensagem.setSubject(assunto);
        mensagem.setText(conteudo);

        javaMailSender.send(mensagem);
    }
}
