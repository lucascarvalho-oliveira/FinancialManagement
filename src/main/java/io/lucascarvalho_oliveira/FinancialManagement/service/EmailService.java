package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.dto.EmailRespostaDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public String gerarCodigo(){
        int codigo = 100000 + new Random().nextInt(900000);

        return String.valueOf(codigo);
    }

    public EmailRespostaDto envioEmail(String email, String codigo){
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(email);
        mensagem.setSubject("Verificação de e-mail");
        mensagem.setText("Seu código de verificação é: " + codigo);

        mailSender.send(mensagem);

        EmailRespostaDto dto = new EmailRespostaDto("Email enviado com sucesso!", codigo);

        return dto;
    }
}
