package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.bancolombia.r2dbc.entity.LoanApplicationEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class LoanReactiveRepositoryAdapter
    extends ReactiveAdapterOperations<LoanApplication, LoanApplicationEntity, UUID, LoanReactiveRepository>
    implements LoanApplicationRepository {

    private final TransactionalOperator transactionalOperator;

    public LoanReactiveRepositoryAdapter(
        LoanReactiveRepository repository,
        ObjectMapper mapper,
        TransactionalOperator transactionalOperator
    ) {
        super(repository, mapper, entity -> new LoanApplication(
            entity.getId() != null ? entity.getId().toString() : null,
            entity.getDocumentNumber(),
            entity.getAmount(),
            entity.getTermMonths(),
            entity.getLoanTypeId(),
            entity.getStatusCode()
        ));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<LoanApplication> save(LoanApplication model) {
        LoanApplicationEntity entity = new LoanApplicationEntity();
        entity.setDocumentNumber(model.documentNumber());
        entity.setAmount(model.amount());
        entity.setTermMonths(model.termMonths());
        entity.setLoanTypeId(model.loanTypeId());
        entity.setStatusCode(model.statusCode() != null ? model.statusCode() : "1");

        return transactionalOperator.transactional(
            repository.save(entity)
                .map(saved -> new LoanApplication(
                    saved.getId().toString(),
                    saved.getDocumentNumber(),
                    saved.getAmount(),
                    saved.getTermMonths(),
                    saved.getLoanTypeId(),
                    saved.getStatusCode()
                ))
        );
    }

    @Override
    public Mono<LoanApplication> findByDocumentNumber(String email) {
        return repository.findByDocumentNumber(email)
            .switchIfEmpty(Mono.empty())
            .map(e -> mapper.map(e, LoanApplication.class));
    }
}
