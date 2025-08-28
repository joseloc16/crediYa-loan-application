package co.com.bancolombia.usecase.createloanapplication.input;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import reactor.core.publisher.Mono;

public interface CreateLoanUseCasePort {
    Mono<LoanApplication> execute(LoanApplication application);
}
