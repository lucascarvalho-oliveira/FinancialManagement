package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.ContaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ContaService {
    private final ContaRepository repositoryConta;
    private final PessoaService servicePessoa;

    public ContaService(ContaRepository repositoryConta, PessoaService servicePessoa){
        this.repositoryConta = repositoryConta;
        this.servicePessoa = servicePessoa;
    }

    public Conta salvarConta(Conta conta){
        Conta contaSalva = repositoryConta.save(conta);
        servicePessoa.somarConta(conta, conta.getPessoa().getId());

        return contaSalva;
    }

    public Conta atualizarConta(Conta conta){
        Conta contaAtualizada = repositoryConta.save(conta);
        servicePessoa.somarConta(conta, conta.getPessoa().getId());

        return contaAtualizada;
    }

    public void deleteConta(Integer idConta){

        Conta conta = repositoryConta.findById(idConta)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

        repositoryConta.delete(conta);

        servicePessoa.somarConta(conta, conta.getPessoa().getId());
    }
}
