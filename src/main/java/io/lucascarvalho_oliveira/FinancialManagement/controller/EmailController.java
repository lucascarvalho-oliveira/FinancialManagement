package io.lucascarvalho_oliveira.FinancialManagement.controller;

import io.lucascarvalho_oliveira.FinancialManagement.dto.EamilRequestDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.EmailRespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.RespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.VerificacaoRequestDto;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.service.CodigoVerificacaoService;
import io.lucascarvalho_oliveira.FinancialManagement.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class EmailController {
    private final EmailService serviceEmail;
    private final CodigoVerificacaoService serviceVerificacao;

    public EmailController(EmailService serviceEmail, CodigoVerificacaoService serviceVerificacao){
        this.serviceEmail = serviceEmail;
        this.serviceVerificacao = serviceVerificacao;
    }

    @PostMapping("emailPessoa")
    public ResponseEntity<EmailRespostaDto> envioEmail(@RequestBody EamilRequestDto dto){
        String codigo = serviceEmail.gerarCodigo();

        serviceVerificacao.salvarVerificacao(dto.email(), codigo);

        return ResponseEntity.ok(serviceEmail.envioEmail(dto.email(),  codigo));
    }

    @PostMapping("validacao")
    public ResponseEntity<RespostaDto> verificacao(@RequestBody VerificacaoRequestDto dto){

        return  ResponseEntity.ok(serviceVerificacao.verificarEmail(dto.email(), dto.codigo()));
    }
}
