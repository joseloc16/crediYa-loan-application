package co.com.bancolombia.model.loanapplication.gateways;

import co.com.bancolombia.model.loanapplication.dto.User;
import reactor.core.publisher.Mono;

public interface UserGateway {
    Mono<User> findByIdentityDocument(String identityDocument);
}
