package co.com.bancolombia.usecase.createloanapplication;

import java.math.BigDecimal;

public record CreateLoanApplicationCommand(
    BigDecimal amount,
    Integer termMonths,
    String email,
    String loanTypeId
) {
}
