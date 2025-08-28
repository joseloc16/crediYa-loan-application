package co.com.bancolombia.config;

import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.model.loanapplication.gateways.UserGateway;
import co.com.bancolombia.usecase.createloanapplication.CreateLoanApplicationUseCase;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanUseCasePort;
import co.com.bancolombia.usecase.getloanapplication.input.GetLoanUseCasePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

        @Bean
        public CreateLoanUseCasePort userUseCasePort(LoanApplicationRepository repository, UserGateway userGateway) {
                return new CreateLoanApplicationUseCase(repository, userGateway);
        }

        @Bean
        public GetLoanUseCasePort getLoanUseCasePort(LoanApplicationRepository repository) {
                return repository::findById;
        }
}
