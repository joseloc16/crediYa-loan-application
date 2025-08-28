package co.com.bancolombia.usecase.getloanapplication.input;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import reactor.core.publisher.Mono;

public interface GetLoanUseCasePort {
    Mono<LoanApplication> byId(String id);
}
