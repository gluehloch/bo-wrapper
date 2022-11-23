package de.betoffice.wrapper.api;

public interface OperationResult<T> {

    T result();
    T orThrow() throws OperationException;
    Throwable exeption();
    boolean success();

}
