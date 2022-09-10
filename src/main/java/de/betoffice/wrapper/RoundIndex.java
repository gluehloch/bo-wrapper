package de.betoffice.wrapper;

import java.util.Objects;

public class RoundIndex {

    private final int index;

    private RoundIndex(int index) {
        this.index = index;
    }

    public static RoundIndex of(int index) {
        if (index < 1) {
            throw new IndexOutOfBoundsException("index is smaller than 1");
        }

        return new RoundIndex(index);
    }

    int index() {
        return index;
    }
}
