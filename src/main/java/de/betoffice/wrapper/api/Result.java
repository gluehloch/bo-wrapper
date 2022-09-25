package de.betoffice.wrapper.api;

public interface Result<T> {

    T result();
    Throwable exeption();
    boolean success();
}
