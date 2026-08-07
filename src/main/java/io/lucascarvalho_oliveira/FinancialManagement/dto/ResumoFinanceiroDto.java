package io.lucascarvalho_oliveira.FinancialManagement.dto;

import java.math.BigDecimal;

public record ResumoFinanceiroDto(
        BigDecimal totalReceita,
        BigDecimal totalDespesa,
        BigDecimal saldo
) {
}
