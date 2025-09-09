package co.com.bancolombia.usecase.createloanapplication.input;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.usecase.createloanapplication.CreateLoanApplicationCommand;
import reactor.core.publisher.Mono;

public interface CreateLoanApplicationUseCase {
    Mono<LoanApplication> applyFor(CreateLoanApplicationCommand application);
}
