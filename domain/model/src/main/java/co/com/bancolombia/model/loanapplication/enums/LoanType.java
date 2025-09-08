package co.com.bancolombia.model.loanapplication.enums;

import co.com.bancolombia.model.loanapplication.exceptions.BussinessException;
import co.com.bancolombia.model.loanapplication.exceptions.LoanTypeNotFoundException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

@RequiredArgsConstructor
public enum LoanType {
    PERSONAL(1L, new BigDecimal("8000"), 3, 24, Boolean.TRUE),
    VEHICLE (2L, new BigDecimal("100000"), 12, 60, Boolean.FALSE),;

    private final Long id;
    private final BigDecimal maxAmount;
    private final int minTerm;
    private final int maxTerm;
    private final Boolean isAutomaticApproval;

    public static LoanType fromId(Long id) {
        return Arrays.stream(values())
            .filter(t -> t.id.equals(id))
            .findFirst()
            .orElseThrow(() -> new LoanTypeNotFoundException("Loan type with id " + id + " not found"));
    }

    public void validate(BigDecimal amount, int termMonths) {
        if (amount.signum() <= 0 || amount.compareTo(maxAmount) > 0) throw new BussinessException("Amount out of range");
        if(termMonths < minTerm || termMonths > maxTerm) throw new BussinessException("Term out of range");
    }

}
