package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.dto.LoginDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.LoginRespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.dto.ResumoFinanceiroDto;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.PessoaExistente;
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
        if(repositoryPessoa.findByEmail(pessoa.getEmail()).isPresent()){
            throw new PessoaExistente("Pessoa ja cadastrada");
        }

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

    public LoginRespostaDto login(LoginDto dto){
        Pessoa pessoaEncontrada = repositoryPessoa.findByEmail(dto.email())
                .orElseThrow(() -> new PessoaNaoEncontradaException("Email invalido"));

        if(!pessoaEncontrada.getSenha().equals(dto.senha())){
            throw new SenhaInvalidaException("Senha invalida");
        }
        return new LoginRespostaDto("Login feito com sucesso");
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

    public ResumoFinanceiroDto resumoFinanceiro(Integer id){
        Pessoa pessoa = repositoryPessoa.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa não encontrada"));

        BigDecimal receita = pessoa.getTotalReceita();
        BigDecimal despesa = pessoa.getTotalDespesa();
        BigDecimal saldo = receita.subtract(despesa);

        return new ResumoFinanceiroDto(receita, despesa, saldo);
    }
}
