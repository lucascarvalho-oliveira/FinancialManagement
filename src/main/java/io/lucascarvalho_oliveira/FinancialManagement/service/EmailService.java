package io.lucascarvalho_oliveira.FinancialManagement.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import io.lucascarvalho_oliveira.FinancialManagement.dto.EmailRespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.EmailEnvioExeption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final Resend resend;

    public EmailService(@Value("${resend.api-key}") String apiKey){
        this.resend = new Resend(apiKey);
    }

    public String gerarCodigo(){
        int codigo = 100000 + new Random().nextInt(900000);

        return String.valueOf(codigo);
    }

    public EmailRespostaDto envioEmail(String email, String codigo){

        try {
            CreateEmailOptions emailOptions = CreateEmailOptions.builder()
                    .from("Financial Management <noreply@lucascode.api.br>")
                    .to(email)
                    .subject("Código de verificação")
                    .html("<h2>Verificação de e-mail</h2>" +
                            "<p>Seu código de verificação é:</p>" +
                            "<h1>" + codigo + "</h1>"
                    ).build();

            resend.emails().send(emailOptions);
        }catch (ResendException e){
            throw new EmailEnvioExeption("Erro ao enviar o email");
        }

        return new EmailRespostaDto("Email enviado com sucesso!", codigo);
    }
}
