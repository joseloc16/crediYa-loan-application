package co.com.bancolombia.usecase.createloanapplication;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.model.loanapplication.enums.LoanType;
import co.com.bancolombia.model.loanapplication.exceptions.UserNotFoundException;
import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.model.loanapplication.gateways.UserGateway;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanUseCasePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateLoanApplicationUseCase implements CreateLoanUseCasePort {

    private final LoanApplicationRepository loanRepository;
    private final UserGateway userGateway;

    @Override
    public Mono<LoanApplication> execute(LoanApplication application) {
        return validateUserExists(application.getEmail())
            .then(validateTypeExists(application))
            .then(loanRepository.save(application));
    }

    private Mono<Void> validateUserExists(String email) {
        return userGateway.existsByEmail(email)
            .filter(exists -> exists)
            .switchIfEmpty(Mono.error(new UserNotFoundException(email)))
            .then();
    }

    private Mono<Void> validateTypeExists(LoanApplication application) {
        return Mono.fromRunnable(() -> {
            LoanType type = LoanType.fromId(application.getLoanTypeId());
            type.validate(application.getAmount(), application.getTermMonths());
        });
    }
}
