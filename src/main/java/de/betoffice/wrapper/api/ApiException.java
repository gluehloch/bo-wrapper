package de.betoffice.wrapper.api;

public class ApiException extends RuntimeException {

    private final Throwable cause;
    
    public static ApiException of(Throwable cause) {
        return new ApiException(cause);
    }
    
    private ApiException(Throwable cause) {
        this.cause = cause;
    }
    
    public Throwable getCause() {
        return cause;
    }
    
}
