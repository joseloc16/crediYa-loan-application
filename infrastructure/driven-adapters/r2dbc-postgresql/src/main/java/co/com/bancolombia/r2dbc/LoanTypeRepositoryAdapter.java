package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.loanapplication.LoanType;
import co.com.bancolombia.model.loanapplication.gateways.LoanTypeRepository;
import co.com.bancolombia.r2dbc.entity.LoanTypeEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanTypeRepositoryAdapter
    extends ReactiveAdapterOperations<LoanType, LoanTypeEntity, String, LoanTypeReactiveRepository>
    implements LoanTypeRepository {

    public LoanTypeRepositoryAdapter(LoanTypeReactiveRepository repository,
                                     ObjectMapper mapper) {
        super(repository, mapper, e -> new LoanType(
            e.getId(),
            e.getName(),
            e.getMinAmount(),
            e.getMaxAmount(),
            e.getInterestRate(),
            Boolean.TRUE.equals(e.getAutoValidation())
        ));
    }
}
