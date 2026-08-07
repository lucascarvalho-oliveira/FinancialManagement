package io.lucascarvalho_oliveira.FinancialManagement.controller;

import io.lucascarvalho_oliveira.FinancialManagement.dto.ContaDto;
import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import io.lucascarvalho_oliveira.FinancialManagement.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conta")
public class ContaController {
    private final ContaService serviceConta;

    public ContaController(ContaService serviceConta){
        this.serviceConta = serviceConta;
    }

    @PostMapping("salvar")
    public ResponseEntity<Conta> salvarPessoa(@RequestBody Conta conta){
        Conta salvarConta = serviceConta.salvarConta(conta);

        return ResponseEntity.ok(salvarConta);
    }

    @GetMapping("listar")
    public ResponseEntity<List<ContaDto>> ListarConta(){
        List<ContaDto> contas = serviceConta.listarConta();

        return ResponseEntity.ok(contas);
    }

    @PutMapping("atualizar/{id}/valor")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Integer id, @RequestBody Conta conta){
        Conta contaAtualizada = serviceConta.atualizarConta(id, conta.getValor());

        return ResponseEntity.ok(contaAtualizada);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Integer id){
        serviceConta.deleteConta(id);

        return ResponseEntity.noContent().build();
    }
}
