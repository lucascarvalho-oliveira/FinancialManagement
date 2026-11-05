package io.lucascarvalho_oliveira.FinancialManagement.service;

import io.lucascarvalho_oliveira.FinancialManagement.dto.RespostaDto;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.CodigoIncorretoExeption;
import io.lucascarvalho_oliveira.FinancialManagement.model.CodigoVerificacao;
import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import io.lucascarvalho_oliveira.FinancialManagement.repository.CodigoVerificacaoRepository;
import io.lucascarvalho_oliveira.FinancialManagement.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodigoVerificacaoServiceTest {

    @Mock
    private CodigoVerificacaoRepository repositoryVerificacao;

    @Mock
    private PessoaRepository repositoryPessoa;

    @InjectMocks
    private CodigoVerificacaoService service;

    @Test
    void deveRecusarCodigoExpirado() {
        CodigoVerificacao codigo = new CodigoVerificacao();
        codigo.setExpiracao(LocalDateTime.now().minusMinutes(1));
        when(repositoryVerificacao.findByEmailAndCodigo("a@a.com", "123"))
                .thenReturn(Optional.of(codigo));

        assertThrows(RuntimeException.class,
                () -> service.verificarEmail("a@a.com", "123"));

        verify(repositoryVerificacao).deleteByEmailAndCodigo("a@a.com", "123");
        verify(repositoryPessoa, never()).save(any());
    }

    @Test
    void deveRecusarCodigoIncorreto(){
        when(repositoryVerificacao.findByEmailAndCodigo("a@a.com", "999"))
                .thenReturn(Optional.empty());
        assertThrows(CodigoIncorretoExeption.class,
                ()->service.verificarEmail("a@a.com", "999"));
    }

    @Test
    void deveVerificarEmailComCodigoValido() {
        CodigoVerificacao codigo = new CodigoVerificacao();
        codigo.setExpiracao(LocalDateTime.now().plusMinutes(10));
        when(repositoryVerificacao.findByEmailAndCodigo("a@a.com", "123"))
                .thenReturn(Optional.of(codigo));

        Pessoa pessoa = new Pessoa("Lucas", "a@a.com", "123456");
        when(repositoryPessoa.findByEmail("a@a.com"))
                .thenReturn(Optional.of(pessoa));

        RespostaDto resposta = service.verificarEmail("a@a.com", "123");

        assertTrue(pessoa.isEmailVerificado());
        verify(repositoryPessoa).save(pessoa);
        assertEquals("Código valido!", resposta.mensagem());
    }
}