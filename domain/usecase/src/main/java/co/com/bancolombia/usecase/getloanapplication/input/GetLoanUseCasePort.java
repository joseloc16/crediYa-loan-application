package co.com.bancolombia.usecase.getloanapplication.input;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetLoanUseCasePort {
    Mono<LoanApplication> getById(String id);
    Flux<LoanApplication> getByEmail(String email);
}
