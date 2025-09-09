package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.CreateLoanApplicationRequest;
import co.com.bancolombia.api.dto.LoanApplicationResponse;
import co.com.bancolombia.api.mapper.LoanApplicationMapper;
import co.com.bancolombia.usecase.createloanapplication.input.CreateLoanApplicationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RestController
@RequestMapping(value = "/api/v1/solicitud", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Solicitudes", description = "Endpoints para crear y consultar solicitudes de préstamo")
public class LoanApplicationsController {

    private final CreateLoanApplicationUseCase loanUseCase;
    private final RequestValidator requestValidator;
    private final LoanApplicationMapper mapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        operationId = "createLoanApplication",
        summary = "Crear solicitud de préstamo",
        description = "Valida el request, ejecuta el caso de uso y retorna la solicitud creada.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = CreateLoanApplicationRequest.class),
                examples = {
                    @ExampleObject(
                        name = "requestValido",
                        value = """
                    {
                      "amount": 1500.00,
                      "termMonths": 4,
                      "email": "jose2.nunez@pragma.com",
                      "loanTypeId": "2"
                    }
                    """
                    ),
                    @ExampleObject(
                        name = "requestInvalido_termMonths",
                        value = """
                    {
                      "amount": 1500.00,
                      "termMonths": 0,
                      "email": "jose2.nunez@pragma.com",
                      "loanTypeId": "1"
                    }
                    """
                    )
                }
            )
        )
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Solicitud creada",
            content = @Content(
                schema = @Schema(implementation = LoanApplicationResponse.class),
                examples = @ExampleObject(
                    name = "created",
                    value = """
                {
                  "id": "6dd9167e-9ace-45fb-bad7-eeab6a8bbc17",
                  "email": "jose2.nunez@pragma.com",
                  "amount": 1500.00,
                  "termMonths": 4,
                  "loanTypeId": "2",
                  "status": "1"
                }
                """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error de validación",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "validationError",
                    value = """
                {
                  "timestamp": "2025-08-28 18:53:20 -05:00",
                  "status": 400,
                  "error": "Bad Request",
                  "code": "ERROR",
                  "message": "[{message=debe ser mayor que o igual a 3, field=termMonths}]"
                }
                """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tipo de préstamo no encontrado",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "typeNotFound",
                    value = """
                {
                  "timestamp": "2025-08-28 18:53:51 -05:00",
                  "status": 404,
                  "error": "Not Found",
                  "code": "TYPE_NOT_FOUND",
                  "message": "TypeId not found: 3",
                  "details": [
                    {
                      "message": "TypeId not found: 3",
                      "field": "loanTypeId"
                    }
                  ]
                }
                """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "internalError",
                    value = """
                {
                  "timestamp": "2025-08-28T23:14:05Z",
                  "status": 500,
                  "error": "Internal Server Error",
                  "code": "ERROR",
                  "message": "Error interno"
                }
                """
                )
            )
        )
    })
    public Mono<LoanApplicationResponse> createLoanApplication(
        @org.springframework.web.bind.annotation.RequestBody CreateLoanApplicationRequest request
    ) {
        return requestValidator.validate(request)
            .doOnNext(dto -> log.debug("validated request documentNumber={}", dto.documentNumber()))
            .map(mapper::toCommand)
            .flatMap(loanUseCase::applyFor)
            .map(mapper::toResponse)
            .doOnSuccess(r -> log.info("POST /api/v1/solicitud <- 201 Created"))
            .doOnError(e -> log.warn("POST /api/v1/solicitud <- error: {}", e.toString()))
            .onErrorMap(ValidationException.class,
                e -> new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e));
    }
}
