package io.lucascarvalho_oliveira.FinancialManagement.exception;

import java.time.LocalDate;

public record ErroResponse(
        int status,
        String mensagem
){}
