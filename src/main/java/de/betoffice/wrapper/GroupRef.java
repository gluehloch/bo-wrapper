package de.betoffice.wrapper;

import java.util.Objects;

public class GroupRef {

    private final SeasonRef seasonRef;
    private final GroupTypeRef groupTypeRef;

    private GroupRef(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        this.seasonRef = seasonRef;
        this.groupTypeRef = groupTypeRef;
    }

    public static GroupRef of(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        Objects.requireNonNull(seasonRef);
        Objects.requireNonNull(groupTypeRef);
        return new GroupRef(seasonRef, groupTypeRef);
    }

    SeasonRef season() {
        return seasonRef;
    }

    GroupTypeRef groupType() {
        return groupTypeRef;
    }

}
