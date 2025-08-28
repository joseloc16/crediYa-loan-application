package co.com.bancolombia.api.error;

import co.com.bancolombia.model.loanapplication.exceptions.*;
import jakarta.validation.ValidationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private static final DateTimeFormatter FRIENDLY_TS = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss XXX")
        .withLocale(new Locale("es", "ES"));

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable ex = getError(request);
        HttpStatus status = resolveStatus(ex);
        String code = resolveCode(ex);

        ZoneId zone = ZoneId.systemDefault();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", ZonedDateTime.now(zone).format(FRIENDLY_TS));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("code", code);
        body.put("message", resolveMessage(ex));

        // Detalles por tipo de error
        if (ex instanceof jakarta.validation.ConstraintViolationException cve) {
            body.put("details", cve.getConstraintViolations().stream()
                .map(v -> Map.of("field", v.getPropertyPath().toString(),
                    "message", v.getMessage()))
                .toList());

        } else if (ex instanceof WebExchangeBindException bind) {
            body.put("details", bind.getFieldErrors().stream()
                .map(fe -> Map.of(
                    "field", fe.getField(),
                    "message", fe.getDefaultMessage()))
                .toList());

        } else if (ex instanceof UserNotFoundException unfe) {
            body.put("details", java.util.List.of(
                Map.of("field", "email", "message", unfe.getMessage())
            ));

        } else if (ex instanceof TypeNotFoundException tnfe
            || ex instanceof LoanTypeNotFoundException ltnfe) {
            body.put("details", java.util.List.of(
                Map.of("field", "loanTypeId", "message", ex.getMessage())
            ));

        } else if (ex instanceof ExternalServiceException ese) {
            body.put("details", java.util.List.of(
                Map.of("field", "service", "message", ese.getMessage())
            ));

        } else if (ex instanceof BussinessException be) {
            // Regla de negocio genérica: puedes cambiar "rule" por el campo que prefieras
            body.put("details", java.util.List.of(
                Map.of("field", "rule", "message", be.getMessage())
            ));
        }

        return body;
    }

    private HttpStatus resolveStatus(Throwable ex) {
        // Conflictos (duplicados)
        //if (ex instanceof DuplicateKeyException) return HttpStatus.CONFLICT;

        // No encontrado
        if (ex instanceof UserNotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof TypeNotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof LoanTypeNotFoundException) return HttpStatus.NOT_FOUND;

        // Validación
        if (ex instanceof jakarta.validation.ConstraintViolationException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof WebExchangeBindException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof ValidationException) return HttpStatus.BAD_REQUEST;

        // Reglas de negocio
        if (ex instanceof BussinessException) return HttpStatus.UNPROCESSABLE_ENTITY; // 422

        // Servicios externos
        if (ex instanceof ExternalServiceException) return HttpStatus.SERVICE_UNAVAILABLE; // 503

        // ResponseStatusException explícitas
        if (ex instanceof ResponseStatusException rse)
            return HttpStatus.valueOf(rse.getStatusCode().value());

        // Fallback
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String resolveCode(Throwable ex) {
        //if (ex instanceof DuplicateKeyException) return "RESOURCE_CONFLICT";

        if (ex instanceof UserNotFoundException) return "USER_NOT_FOUND";
        if (ex instanceof TypeNotFoundException) return "TYPE_NOT_FOUND";
        if (ex instanceof LoanTypeNotFoundException) return "LOAN_TYPE_NOT_FOUND";

        if (ex instanceof jakarta.validation.ConstraintViolationException
            || ex instanceof WebExchangeBindException
            || ex instanceof ValidationException) return "VALIDATION_ERROR";

        if (ex instanceof BussinessException) return "BUSINESS_RULE_VIOLATION";
        if (ex instanceof ExternalServiceException) return "EXTERNAL_SERVICE_UNAVAILABLE";

        if (ex instanceof ResponseStatusException) return "ERROR";

        return "INTERNAL_ERROR";
    }

    private String resolveMessage(Throwable ex) {
        if (ex instanceof ResponseStatusException rse) {
            return rse.getReason() != null ? rse.getReason() : rse.getMessage();
        }
        return ex.getMessage();
    }
}
