package de.betoffice.wrapper.impl;

import java.util.Objects;

import de.betoffice.wrapper.api.Result;

class DefaultResult<T> implements Result<T> {

    private final Throwable exception;
    private final T result;

    private DefaultResult(T result, Throwable exception) {
        this.result = result;
        this.exception = exception;
    }

    static <T> Result<T> success(T result) {
        Objects.nonNull(result);
        return new DefaultResult(result, null);
    }

    static <T> Result<T> failure(Throwable exception) {
        Objects.nonNull(exception);
        return new DefaultResult(null, exception);
    }

    @Override
    public T result() {
        return result;
    }

    @Override
    public Throwable exeption() {
        return exception;
    }

    @Override
    public boolean success() {
        return exception == null && result != null;
    }
}
