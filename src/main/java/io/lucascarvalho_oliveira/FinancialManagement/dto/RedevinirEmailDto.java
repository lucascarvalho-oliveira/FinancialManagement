package io.lucascarvalho_oliveira.FinancialManagement.dto;

public record RedevinirEmailDto(
        String emailAntigo,
        String emailNovo,
        String senha
) {
}
