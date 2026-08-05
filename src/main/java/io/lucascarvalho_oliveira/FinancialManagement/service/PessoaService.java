package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.PessoaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.SenhaInvalidaException;
import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.model.enums.TipoConta;
import io.lucascarvalho_oliveira.FinancialManagement.repository.ContaRepository;
import io.lucascarvalho_oliveira.FinancialManagement.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PessoaService {
    private final PessoaRepository repositoryPessoa;
    private final ContaRepository repositoryConta;

    public PessoaService(PessoaRepository repositoryPessoa, ContaRepository repositoryConta){
        this.repositoryPessoa = repositoryPessoa;
        this.repositoryConta = repositoryConta;
    }

    public Pessoa salvarPessoa(Pessoa pessoa){
        if(pessoa.getSenha().length() < 8){
            throw new SenhaInvalidaException("A senha deve ter no minimo 8 caracteres");
        }

        String senha = pessoa.getSenha();
        boolean temSimbolo = senha.matches(".*[^a-zA-Z0-9].*");
        boolean temMaiuscula = senha.matches(".*[A-Z].*");

        if(!temSimbolo || !temMaiuscula){
            throw new SenhaInvalidaException("A senha deve conter pelo menos 1 letra maiúscula ou 1 símbolo");
        }

        return repositoryPessoa.save(pessoa);
    }

    public void confirmarSenha(Pessoa pessoa){
        Pessoa pessoaEncontrada = repositoryPessoa.findByEmail(pessoa.getEmail())
                .orElseThrow(() -> new PessoaNaoEncontradaException("Email invalido"));

        if(!pessoa.getSenha().equals(pessoaEncontrada.getSenha())){
            throw new SenhaInvalidaException("Senha invalida");
        }
    }

    public void somarConta(Conta conta, Integer idPessoa){

        Pessoa pessoa = repositoryPessoa.findById(idPessoa)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Usuário não encontrado"));

        if(conta.getTipoConta() == TipoConta.RECEITA){
            BigDecimal soma = repositoryConta.totalReceitaPessoa(pessoa.getId());
            pessoa.setTotalReceita(soma);
        }

        if(conta.getTipoConta() == TipoConta.DESPESA){
            BigDecimal soma = repositoryConta.totalDespesaPessoa(pessoa.getId());
            pessoa.setTotalDespesa(soma);
        }

        repositoryPessoa.save(pessoa);
    }
}
