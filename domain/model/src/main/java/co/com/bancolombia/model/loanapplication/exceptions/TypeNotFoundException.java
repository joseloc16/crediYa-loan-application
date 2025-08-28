package co.com.bancolombia.model.loanapplication.exceptions;

public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(String typeId) {
        super("TypeId not found: " + typeId);
    }
}
