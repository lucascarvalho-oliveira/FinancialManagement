package io.lucascarvalho_oliveira.FinancialManagement.repository;

import io.lucascarvalho_oliveira.FinancialManagement.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Optional<Pessoa> findByEmail(String email);
}
