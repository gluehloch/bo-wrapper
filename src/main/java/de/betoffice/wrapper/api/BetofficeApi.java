package de.betoffice.wrapper.api;

import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface BetofficeApi {
    GroupTypeRef groupType(String groupTypeName);

    TeamRef team(String teamShortName, String teamLongName);

    SeasonRef season(String name, String year, SeasonType type, TeamType teamType);

    SeasonRef group(GroupTypeRef groupTypeRef, SeasonRef seasonRef);

    RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, LocalDateTime ldt);
    
    RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt);

    ZonedDateTime toZonedDateTime(LocalDateTime ldt);

    LocalDateTime toDate(String dateTimeAsString);
}
