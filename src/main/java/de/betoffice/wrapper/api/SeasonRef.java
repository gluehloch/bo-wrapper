package de.betoffice.wrapper.api;

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

    public String name() {
        return name;
    }

    public String year() {
        return year;
    }

}
