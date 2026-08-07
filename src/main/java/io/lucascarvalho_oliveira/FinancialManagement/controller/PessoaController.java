package io.lucascarvalho_oliveira.FinancialManagement.controller;

import io.lucascarvalho_oliveira.FinancialManagement.dto.LoginDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.LoginRespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.ResumoFinanceiroDto;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Scanner;

@RestController
@RequestMapping("pessoa")
public class PessoaController {

    private final PessoaService servicePessoa;

    public PessoaController(PessoaService servicePessoa){
        this.servicePessoa = servicePessoa;
    }

    @PostMapping("salvar")
    public ResponseEntity<Pessoa> salvarPessoa(@RequestBody Pessoa pessoa){
        Pessoa pessoaSalva = servicePessoa.salvarPessoa(pessoa);

        return ResponseEntity.ok(pessoaSalva);
    }

    @PostMapping("login")
    public ResponseEntity<LoginRespostaDto> login (@RequestBody LoginDto dto){
        LoginRespostaDto pessoa = servicePessoa.login(dto);

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("resumo/{id}")
    public ResponseEntity<ResumoFinanceiroDto> resumo(@PathVariable Integer id){
        return ResponseEntity.ok(servicePessoa.resumoFinanceiro(id));
    }
}
