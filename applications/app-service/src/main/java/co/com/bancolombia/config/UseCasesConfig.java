package co.com.bancolombia.config;

import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.model.loanapplication.gateways.LoanTypeRepository;
import co.com.bancolombia.model.loanapplication.gateways.UserRepository;
import co.com.bancolombia.usecase.createloanapplication.CreateLoanApplicationApplicationService;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanApplicationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public CreateLoanApplicationUseCase createLoanUseCase(
        UserRepository userRepository,
        LoanTypeRepository loanTypeRepo,
        LoanApplicationRepository loanRepo
    ) {
        return new CreateLoanApplicationApplicationService(userRepository, loanTypeRepo, loanRepo);
    }
}
