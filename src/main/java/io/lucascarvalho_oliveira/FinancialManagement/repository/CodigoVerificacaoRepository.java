package io.lucascarvalho_oliveira.FinancialManagement.repository;

import io.lucascarvalho_oliveira.FinancialManagement.model.CodigoVerificacao;
import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoVerificacaoRepository extends JpaRepository<CodigoVerificacao, Integer> {
    Optional<CodigoVerificacao> findByEmailAndCodigo(String email, String codigo);

    void deleteByEmailAndCodigo(String email, String codigo);
}
