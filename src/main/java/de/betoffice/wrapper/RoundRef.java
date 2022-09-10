package de.betoffice.wrapper;

public class RoundRef {

    private final SeasonRef seasonRef;
    private final RoundIndex roundIndex;
    private final GroupTypeRef groupTypeRef;

    private RoundRef(SeasonRef seasonRef, RoundIndex roundIndex, GroupTypeRef groupTypeRef) {
        this.seasonRef = seasonRef;
        this.roundIndex = roundIndex;
        this.groupTypeRef = groupTypeRef;
    }

    public static RoundRef of(SeasonRef seasonRef, RoundIndex index, GroupTypeRef groupTypeRef) {
        return new RoundRef(seasonRef, index, groupTypeRef);
    }

    SeasonRef season() {
        return seasonRef;
    }

    RoundIndex index() {
        return roundIndex;
    }

    GroupTypeRef groupType() {
        return groupTypeRef;
    }

}
