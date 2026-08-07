package io.lucascarvalho_oliveira.FinancialManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.lucascarvalho_oliveira.FinancialManagement.model.enums.TipoConta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaDto(
        Integer id,
        String nome,
        BigDecimal valor,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        String mes,
        TipoConta tipoConta
) {
}
