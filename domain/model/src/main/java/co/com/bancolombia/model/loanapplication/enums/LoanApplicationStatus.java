package co.com.bancolombia.model.loanapplication.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanApplicationStatus {
    PENDING_REVIEW("Pending Review", 1),
    APPROVED("Approved", 2),
    REJECTED("Rejected", 3);

    private final String description;
    private final int code;
}
