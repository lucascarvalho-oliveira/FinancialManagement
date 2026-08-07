package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.dto.ContaDto;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.ContaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import io.lucascarvalho_oliveira.FinancialManagement.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public List<ContaDto> listarConta(){

        return repositoryConta.findAll().stream()
                .map(conta -> new ContaDto(
                        conta.getId(),
                        conta.getNome(),
                        conta.getValor(),
                        conta.getData(),
                        conta.getMes(),
                        conta.getTipoConta()
                )).toList();
    }

    public Conta atualizarConta(Integer id, BigDecimal valor){

        Conta contaAtualizar = repositoryConta.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta nao encontrada"));

        contaAtualizar.setValor(valor);

        Conta contaAtualizada = repositoryConta.save(contaAtualizar);
        servicePessoa.somarConta(contaAtualizada, contaAtualizada.getPessoa().getId());

        return contaAtualizada;
    }

    public void deleteConta(Integer idConta){

        Conta conta = repositoryConta.findById(idConta)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

        repositoryConta.delete(conta);

        servicePessoa.somarConta(conta, conta.getPessoa().getId());
    }
}
