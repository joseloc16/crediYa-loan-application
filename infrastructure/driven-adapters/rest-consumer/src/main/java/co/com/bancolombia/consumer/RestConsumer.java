package co.com.bancolombia.consumer;

import co.com.bancolombia.model.loanapplication.exceptions.ExternalServiceException;
import co.com.bancolombia.model.loanapplication.gateways.UserGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RestConsumer implements UserGateway {
    private final WebClient authWebClient;

    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
    @CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/)
    public Mono<ObjectResponse> testGet() {
        return authWebClient
                .get()
                .retrieve()
                .bodyToMono(ObjectResponse.class);
    }

// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() // TODO: change for another endpoint or destination
//                .retrieve()
//                .bodyToMono(String.class);
//    }

    @CircuitBreaker(name = "testPost")
    public Mono<ObjectResponse> testPost() {
        UserRequest request = UserRequest.builder()
            .email("exampleval1")
            .build();
        return authWebClient
                .post()
                .body(Mono.just(request), UserRequest.class)
                .retrieve()
                .bodyToMono(ObjectResponse.class);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return authWebClient.get()
            .uri(uri -> uri.path("/api/v1/usuarios/exists").queryParam("email", email).build())
            .retrieve()
            .bodyToMono(Boolean.class)
            .timeout(Duration.ofSeconds(2))
            .onErrorResume(e -> Mono.error(new ExternalServiceException("Auth unavailable")));
    }
}
