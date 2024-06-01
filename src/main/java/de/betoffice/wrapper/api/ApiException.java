package de.betoffice.wrapper.api;

import java.util.Objects;

@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

    private final Throwable cause;
    
    public static ApiException of(Throwable cause) {
        Objects.requireNonNull(cause);
        return new ApiException(cause);
    }
    
    private ApiException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.cause = cause;
    }
    
    public Throwable getCause() {
        return cause;
    }
    
}
