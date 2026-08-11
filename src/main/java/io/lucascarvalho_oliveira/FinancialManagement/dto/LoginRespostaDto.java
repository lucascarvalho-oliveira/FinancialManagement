package io.lucascarvalho_oliveira.FinancialManagement.dto;

import org.antlr.v4.runtime.Token;

public record LoginRespostaDto(
        String Mensagem,
        String Token
) {
}
