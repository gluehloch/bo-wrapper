package de.betoffice.wrapper.impl;

import de.betoffice.wrapper.api.Result;

class DefaultResult<T> implements Result<T> {

    private final RuntimeException exception;
    private final T result;

    private DefaultResult(T result, RuntimeException exception) {
        this.result = result;
        this.exception = exception;
    }

    static <T> Result<T> success(T result) {
        return new DefaultResult(result, null);
    }

    static <T> Result<T> failure(RuntimeException exception) {
        return new DefaultResult(null, exception);
    }

    @Override
    public T result() {
        return result;
    }

    @Override
    public RuntimeException exeption() {
        return exception;
    }
}
