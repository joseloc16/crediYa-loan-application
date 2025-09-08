package co.com.bancolombia.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateLoanApplicationRequest(

    @NotNull(message = "Documento de identidad es requerido")
    @NotBlank(message = "Documento de identidad no debe estar vacio")
    @JsonProperty("documento_identidad")
    String documentNumber,

    @NotNull(message = "El monto es requerido")
    //@DecimalMin("1000.00")
    @JsonProperty("monto")
    BigDecimal amount,

    @NotNull(message = "El plazo en meses es requerido")
    //@Min(3)
    @JsonProperty("plazo")
    Integer termMonths,

    String email,

    @NotNull(message = "El estado de solicitud es requerido")
    @NotBlank(message = "El estado de solicitud no debe estar vacio")
    @JsonProperty("estado")
    String stateId,

    @NotNull(message = "El tipo de solicitud es requerido")
    @NotBlank(message = "El tipo de solicitud no debe estar vacio")
    @JsonProperty("tipo_prestamo")
    String loanTypeId
) {}
