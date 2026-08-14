package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.dto.VerificacaoRespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.CodigoIncorretoExeption;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.PessoaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.model.CodigoVerificacao;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.repository.CodigoVerificacaoRepository;
import io.lucascarvalho_oliveira.FinancialManagement.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodigoVerificacaoService {

    private final CodigoVerificacaoRepository repositoryVerificacao;
    private final PessoaRepository repositoryPessoa;

    public CodigoVerificacaoService(CodigoVerificacaoRepository repositoryVerificacao, PessoaRepository repositoryPessoa){
        this.repositoryVerificacao = repositoryVerificacao;
        this.repositoryPessoa = repositoryPessoa;
    }

    public CodigoVerificacao salvarVerificacao(String email, String codigo){
        CodigoVerificacao verificacao = new CodigoVerificacao();

        verificacao.setEmail(email);
        verificacao.setCodigo(codigo);
        verificacao.setExpiracao(LocalDateTime.now().plusMinutes(15));

        return  repositoryVerificacao.save(verificacao);
    }

    public VerificacaoRespostaDto verificarEmail(String email, String codigo){
        CodigoVerificacao encontrado = repositoryVerificacao.findByEmailAndCodigo(email, codigo)
                .orElseThrow(() -> new CodigoIncorretoExeption("Código invalido"));

        if(encontrado.getExpiracao().isBefore(LocalDateTime.now())){
            repositoryVerificacao.deleteByEmailAndCodigo(email, codigo);

            throw new RuntimeException("Código inspirado!");
        }

        Pessoa pessoa = repositoryPessoa.findByEmail(email)
                        .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa nao encontrada"));

        pessoa.setEmailVerificado(true);
        repositoryPessoa.save(pessoa);

        return new VerificacaoRespostaDto("Código valido!");
    }
}
