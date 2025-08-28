package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.req.CreateLoanApplicationRequest;
import co.com.bancolombia.api.mapper.LoanApplicationMapper;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanUseCasePort;
import co.com.bancolombia.usecase.getloanapplication.input.GetLoanUseCasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final CreateLoanUseCasePort createUC;
    private final GetLoanUseCasePort getUC;
    private final RequestValidator requestValidator;
    private final LoanApplicationMapper mapper;

    public Mono<ServerResponse> registerLoan(CreateLoanApplicationRequest serverRequest) {

        return ServerResponse.ok().bodyValue("");
    }

    /*
    public Mono<ServerResponse> create(ServerRequest req) {
    String bearer = req.headers().firstHeader(HttpHeaders.AUTHORIZATION);
    return req.bodyToMono(CreateLoanApplicationRequest.class)
      .flatMap(ValidationUtils::validate) // escribe un helper que valide con javax
      .map(dto -> LoanApplication.builder()
            .amount(dto.getAmount())
            .termMonths(dto.getTermMonths())
            .email(dto.getEmail())
            .loanTypeId(dto.getLoanTypeId())
            .build())
      .flatMap(createUC::execute)
      .flatMap(app -> ServerResponse.status(HttpStatus.CREATED).bodyValue(app));
  }

  public Mono<ServerResponse> getById(ServerRequest req) {
    return getUC.byId(req.pathVariable("id"))
      .flatMap(app -> ServerResponse.ok().bodyValue(app))
      .switchIfEmpty(ServerResponse.notFound().build());
  }
     */
}
