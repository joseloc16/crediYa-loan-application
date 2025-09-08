package co.com.bancolombia.usecase.createloanapplication;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.model.loanapplication.exceptions.TypeNotFoundException;
import co.com.bancolombia.model.loanapplication.exceptions.UserNotFoundException;
import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.model.loanapplication.gateways.LoanTypeRepository;
import co.com.bancolombia.model.loanapplication.gateways.UserGateway;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanUseCasePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateLoanApplicationService implements CreateLoanUseCasePort {

    private final UserGateway userGateway;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    @Override
    public Mono<LoanApplication> execute(CreateLoanApplicationCommand  application) {
        return userGateway.findByIdentityDocument(application.identityDocument())
            .switchIfEmpty(Mono.error(new UserNotFoundException(application.identityDocument())))
            .flatMap(user -> loanTypeRepository.findById(application.loanTypeId()))
            .switchIfEmpty(Mono.error(new TypeNotFoundException(application.loanTypeId())))
            .flatMap(loan -> {
                LoanApplication toSave =  new LoanApplication.Builder()
                    .email(application.identityDocument())
                    .loanTypeId(application.loanTypeId())
                    .amount(application.amount())
                    .termMonths(application.termMonths())
                    .statusId("1")
                    .build();
                return loanApplicationRepository.save(toSave);
            });
    }
}
