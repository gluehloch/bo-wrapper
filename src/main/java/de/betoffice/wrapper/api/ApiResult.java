package de.betoffice.wrapper.api;

public interface ApiResult<T> {

    T result();
    T orThrow() throws ApiException;
    Throwable exeption();
    boolean success();

}
