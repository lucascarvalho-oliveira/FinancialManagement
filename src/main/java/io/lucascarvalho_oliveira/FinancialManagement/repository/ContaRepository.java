package io.lucascarvalho_oliveira.FinancialManagement.repository;

import io.lucascarvalho_oliveira.FinancialManagement.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
