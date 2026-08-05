package io.lucascarvalho_oliveira.FinancialManagement.repository;

import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

    // Soma os valores de cada conta e retorna o total em totalReceitaPessoa
    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.pessoa.id = :idPessoa AND c.tipoConta = 'RECEITA'")
    BigDecimal totalReceitaPessoa(@Param("idPessoa") Integer idPessoa);

    // Soma os valores de cada conta e retorna o total em totalDespesaPessoa
    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.pessoa.id = :idPessoa AND c.TipoConta = 'DESPESA'")
    BigDecimal totalDespesaPessoa(@Param("idPessoa") Integer idPessoa);

}
