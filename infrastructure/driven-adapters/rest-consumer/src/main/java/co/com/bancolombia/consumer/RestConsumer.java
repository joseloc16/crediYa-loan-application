package co.com.bancolombia.consumer;

import co.com.bancolombia.model.loanapplication.dto.User;
import co.com.bancolombia.model.loanapplication.exceptions.ExternalServiceException;
import co.com.bancolombia.model.loanapplication.exceptions.UserNotFoundException;
import co.com.bancolombia.model.loanapplication.gateways.UserGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements UserGateway {

    private final WebClient authWebClient;

    @Override
    //@CircuitBreaker(name = "auth-find-by-email", fallbackMethod = "findByEmailFallback")
    public Mono<User> findByEmail(String email) {
        return authWebClient.get()
            .uri(u -> u.path("/api/v1/usuarios").queryParam("email", email).build())
            .retrieve()
            .onStatus(s -> s.value() == 404, r -> Mono.error(new UserNotFoundException(email)))
            .bodyToMono(User.class);
    }

    //@SuppressWarnings("unused")
    //private Mono<Boolean> existsByEmailFallback(String email, Throwable ex) {
    //    log.warn("Fallback triggered for existsByEmail, cause: {}", ex.toString());
    //    return Mono.error(new ExternalServiceException("Auth unavailable", ex));
    //}
}
