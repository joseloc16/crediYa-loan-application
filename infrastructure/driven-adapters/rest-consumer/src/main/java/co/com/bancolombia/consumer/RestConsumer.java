package co.com.bancolombia.consumer;

import co.com.bancolombia.model.loanapplication.dto.User;
import co.com.bancolombia.model.loanapplication.exceptions.UserNotFoundException;
import co.com.bancolombia.model.loanapplication.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements UserRepository {

    private final WebClient authWebClient;
    private static final String USUARIO_URL = "/api/v1/usuarios";

    @Override
    public Mono<User> findByDocumentNumber(String documentNumber) {
        return authWebClient.get()
            .uri(u -> u.path(USUARIO_URL + "/{documentNumber}").build(documentNumber))
            .retrieve()
            .onStatus(s -> s.value() == 404, r -> Mono.error(new UserNotFoundException(documentNumber)))
            .bodyToMono(User.class);
    }
}
