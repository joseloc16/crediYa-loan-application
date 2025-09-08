package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.loanapplication.LoanStatus;
import co.com.bancolombia.model.loanapplication.gateways.LoanStatusRepository;
import co.com.bancolombia.r2dbc.entity.LoanStatusEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanStatusRepositoryAdapter
    extends ReactiveAdapterOperations<LoanStatus, LoanStatusEntity, String, LoanStatusReactiveRepository>
    implements LoanStatusRepository {

    public LoanStatusRepositoryAdapter(
        LoanStatusReactiveRepository repository,
        ObjectMapper mapper
    ) {
        super(repository, mapper, d -> mapper.map(d, LoanStatus.class));
    }
}
