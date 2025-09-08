package co.com.bancolombia.model.loanapplication.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record User(
    String firstName,
    String lastName,
    String email,
    String document,
    String phoneNumber,
    String roleId,
    BigDecimal baseSalary,
    Timestamp birthDate) {
}
