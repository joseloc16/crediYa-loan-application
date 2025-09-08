package co.com.bancolombia.model.loanapplication;

import java.math.BigDecimal;

public record LoanType(
    String id,
    String name,
    BigDecimal minAmount,
    BigDecimal maxAmount,
    BigDecimal interestRate,
    boolean autoValidation
) {
}
