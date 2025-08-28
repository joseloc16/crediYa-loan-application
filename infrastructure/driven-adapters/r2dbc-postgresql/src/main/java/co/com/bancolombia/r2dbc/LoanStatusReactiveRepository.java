package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.LoanStatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanStatusReactiveRepository
    extends ReactiveCrudRepository<LoanStatusEntity, String>, ReactiveQueryByExampleExecutor<LoanStatusEntity> {
    Mono<LoanStatusEntity> findById(String id);
}
