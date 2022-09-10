package de.betoffice.wrapper;

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

    String shortName() {
        return shortName;
    }

}
