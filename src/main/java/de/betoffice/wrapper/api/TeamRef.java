package de.betoffice.wrapper.api;

import java.util.Objects;

public class TeamRef {

    private final String shortName;

    private TeamRef(String shortName) {
        this.shortName = shortName;
    }

    public static TeamRef of(String shortName) {
        Objects.requireNonNull(shortName);
        return new TeamRef(shortName);
    }

    public String shortName() {
        return shortName;
    }

}
