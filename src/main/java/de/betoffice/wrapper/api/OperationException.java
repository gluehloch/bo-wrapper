package de.betoffice.wrapper.api;

public class OperationException extends RuntimeException {

    private final Throwable cause;
    
    public OperationException(Throwable cause) {
        this.cause = cause;
    }
    
    public Throwable getCause() {
        return cause;
    }
    
}
