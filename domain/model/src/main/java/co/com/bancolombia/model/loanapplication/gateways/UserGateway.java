package co.com.bancolombia.model.loanapplication.gateways;

import reactor.core.publisher.Mono;

public interface UserGateway {
    Mono<Boolean> existsByEmail(String email);
}
