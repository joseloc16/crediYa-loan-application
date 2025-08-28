package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.CreateLoanApplicationRequest;
import co.com.bancolombia.api.dto.LoanApplicationResponse;
import co.com.bancolombia.api.mapper.LoanApplicationMapper;
import co.com.bancolombia.model.loanapplication.util.EmailUtils;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanUseCasePort;
import co.com.bancolombia.usecase.getloanapplication.input.GetLoanUseCasePort;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RestController
@RequestMapping("/api/v1/solicitud")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final CreateLoanUseCasePort createUC;
    private final GetLoanUseCasePort getUC;
    private final RequestValidator requestValidator;
    private final LoanApplicationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LoanApplicationResponse> create(@RequestBody CreateLoanApplicationRequest request) {
        return requestValidator.validate(request)
            .doOnNext(dto -> log.debug("validated request email={}", EmailUtils.maskEmail(dto.email())))
            .map(mapper::toCommand)
            .flatMap(createUC::execute)
            .map(mapper::toResponse)
            .doOnSuccess(r -> log.info("POST /api/v1/solicitud <- 201 Created"))
            .doOnError(e -> log.warn("POST /api/v1/solicitud <- error: {}", e.toString()))
            .onErrorMap(ValidationException.class,
            e -> new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e));
    }

    @GetMapping(params = "email")
    public Flux<LoanApplicationResponse> getByEmail(@RequestParam String email){
        return null;
    }
}
