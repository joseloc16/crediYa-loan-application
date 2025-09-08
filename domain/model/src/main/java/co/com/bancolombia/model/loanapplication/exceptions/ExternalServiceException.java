package co.com.bancolombia.model.loanapplication.exceptions;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }

    public ExternalServiceException(String authUnavailable, Throwable ex) {
        super(authUnavailable, ex);
    }
}
