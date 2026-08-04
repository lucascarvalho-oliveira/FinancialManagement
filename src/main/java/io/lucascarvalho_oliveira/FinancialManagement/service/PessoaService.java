package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.PessoaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.SenhaInvalidaException;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.repository.PessoaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    private final PessoaRepository repositoryPessoa;

    public PessoaService(PessoaRepository repositoryPessoa){
        this.repositoryPessoa = repositoryPessoa;
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

    public Boolean confirmaSenha(Pessoa pessoa){
        Pessoa pessoaEncontrada = repositoryPessoa.findByEmail(pessoa.getEmail())
                .orElseThrow(() -> new PessoaNaoEncontradaException("Email invalido"));

        if(!pessoa.getSenha().equals(pessoaEncontrada.getSenha())){
            throw new SenhaInvalidaException("Senha invalida");
        }

        return true;
    }
}
