package de.betoffice.wrapper;

import java.util.Objects;

public class SeasonRef {

    private final String name;
    private final String year;

    private SeasonRef(String name, String year) {
        this.name = name;
        this.year = year;
    }

    public static SeasonRef of(String name, String year) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(year);
        return new SeasonRef(name, year);
    }

    String name() {
        return name;
    }

    String year() {
        return year;
    }

}
