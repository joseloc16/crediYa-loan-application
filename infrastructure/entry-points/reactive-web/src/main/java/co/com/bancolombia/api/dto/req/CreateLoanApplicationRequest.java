package co.com.bancolombia.api.dto.req;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateLoanApplicationRequest(

    @NotNull @DecimalMin("1000.00")
    BigDecimal amount,

    @NotNull @Min(3)
    Integer termMonths,

    @NotBlank(message = "email is required")
    @Email(message = "email format is not valid",
        regexp = "^(?!\\.)[A-Za-z0-9._%+-]+(?<!\\.)@"
            + "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"
            + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*"
            + "\\.[A-Za-z]{2,}$")
    String email,

    @NotNull
    Long loanTypeId
) {
}

