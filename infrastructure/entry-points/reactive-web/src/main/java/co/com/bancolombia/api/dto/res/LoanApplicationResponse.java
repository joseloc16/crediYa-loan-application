package co.com.bancolombia.api.dto.res;

import java.math.BigDecimal;

public record LoanApplicationResponse(
    String id,
    String email,
    BigDecimal amount,
    Integer termMonths,
    Long loanTypeId,
    Integer status
) {
}
