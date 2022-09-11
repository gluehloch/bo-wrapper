package de.betoffice.wrapper.api;

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

    public SeasonRef season() {
        return seasonRef;
    }

    public RoundIndex index() {
        return roundIndex;
    }

    public GroupTypeRef groupType() {
        return groupTypeRef;
    }

}
