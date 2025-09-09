package co.com.bancolombia.usecase.createloanapplication;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.model.loanapplication.exceptions.TypeNotFoundException;
import co.com.bancolombia.model.loanapplication.exceptions.UserNotFoundException;
import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.model.loanapplication.gateways.LoanTypeRepository;
import co.com.bancolombia.model.loanapplication.gateways.UserRepository;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanApplicationUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateLoanApplicationApplicationService implements CreateLoanApplicationUseCase {

    private final UserRepository userRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    @Override
    public Mono<LoanApplication> applyFor(CreateLoanApplicationCommand  application) {
        return userRepository.findByDocumentNumber(application.documentNumber())
            .switchIfEmpty(Mono.error(new UserNotFoundException(application.documentNumber())))
            .flatMap(user -> loanTypeRepository.findById(application.loanTypeId()))
            .switchIfEmpty(Mono.error(new TypeNotFoundException(application.loanTypeId())))
            .flatMap(loan -> {
                LoanApplication toSave =  new LoanApplication.Builder()
                    .documentNumber(application.documentNumber())
                    .loanTypeId(application.loanTypeId())
                    .amount(application.amount())
                    .termMonths(application.termMonths())
                    .statusCode("1")
                    .build();
                return loanApplicationRepository.save(toSave);
            });
    }
}
