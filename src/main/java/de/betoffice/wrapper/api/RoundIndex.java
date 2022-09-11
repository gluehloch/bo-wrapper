package de.betoffice.wrapper.api;

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

    public int index() {
        return index;
    }
}
