package de.betoffice.wrapper.impl;

import java.util.function.Supplier;

import de.betoffice.wrapper.api.Result;

public class TryGetCatcher {

     static <T> Result tryGetCatch(Supplier<T> supplier) {
        try {
            return DefaultResult.success(supplier.get());
        } catch (Throwable ex) {
            return DefaultResult.failure(ex);
        }
    }

}
