package co.com.bancolombia.config;

import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.model.loanapplication.gateways.LoanStatusRepository;
import co.com.bancolombia.model.loanapplication.gateways.LoanTypeRepository;
import co.com.bancolombia.model.loanapplication.gateways.UserGateway;
import co.com.bancolombia.usecase.createloanapplication.CreateLoanApplicationUseCase;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanUseCasePort;
import co.com.bancolombia.usecase.getloanapplication.GetLoanApplicationUseCase;
import co.com.bancolombia.usecase.getloanapplication.input.GetLoanUseCasePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

        @Bean
        public CreateLoanUseCasePort createLoanUseCase(
            UserGateway userGateway,
            LoanTypeRepository loanTypeRepo,
            LoanStatusRepository loanStatusRepo,
            LoanApplicationRepository loanRepo
        ) {
                return new CreateLoanApplicationUseCase(userGateway, loanTypeRepo, loanStatusRepo, loanRepo);
        }

        @Bean
        public GetLoanUseCasePort getLoanUseCase(LoanApplicationRepository loanRepo) {
                return new GetLoanApplicationUseCase(loanRepo);
        }
}
