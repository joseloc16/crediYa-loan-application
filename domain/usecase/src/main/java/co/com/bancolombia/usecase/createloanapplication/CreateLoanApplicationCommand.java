package co.com.bancolombia.usecase.createloanapplication;

import java.math.BigDecimal;

public record CreateLoanApplicationCommand(
    String documentNumber,
    BigDecimal amount,
    Integer termMonths,
    String email,
    String stateId,
    String loanTypeId
) {
}
