package de.betoffice.wrapper.api;

public interface OperationResult<T> {

    T result();
    Throwable exeption();
    boolean success();
}
