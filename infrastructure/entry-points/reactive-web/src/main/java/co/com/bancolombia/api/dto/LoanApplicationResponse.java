package co.com.bancolombia.api.dto;

import java.math.BigDecimal;

public record LoanApplicationResponse(
    String id,
    String email,
    BigDecimal amount,
    Integer termMonths,
    String loanTypeId,
    String status
) {
}
