package de.betoffice.wrapper.api;

public interface Result<T> {

    T result();
    RuntimeException exeption();

}
