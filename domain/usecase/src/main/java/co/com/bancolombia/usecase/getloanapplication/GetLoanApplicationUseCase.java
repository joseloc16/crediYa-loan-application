package co.com.bancolombia.usecase.getloanapplication;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.usecase.getloanapplication.input.GetLoanUseCasePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetLoanApplicationUseCase implements GetLoanUseCasePort {
    private final LoanApplicationRepository loanRepository;


    @Override
    public Mono<LoanApplication> getById(String id) {
        return null;
    }

    @Override
    public Flux<LoanApplication> getByEmail(String email) {
        return null;
    }
}
