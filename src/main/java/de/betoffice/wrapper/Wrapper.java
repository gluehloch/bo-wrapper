package de.betoffice.wrapper;

import de.winkler.betoffice.storage.GroupType;
import de.winkler.betoffice.storage.Team;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.winkler.betoffice.service.MasterDataManagerService;
import de.winkler.betoffice.service.SeasonManagerService;
import de.winkler.betoffice.storage.Season;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class Wrapper {

    @Autowired
    private SeasonManagerService seasonManagerService;

    @Autowired
    private MasterDataManagerService masterDataManagerService;
    
    public GroupTypeRef groupType(String groupTypeName) {
        GroupType groupType = new GroupType();
        groupType.setName(groupTypeName);
        masterDataManagerService.createGroupType(groupType);
        return GroupTypeRef.of(groupType.getName());
    }

    public TeamRef team(String teamShortName, String teamLongName) {
        Team team = Team.TeamBuilder.team(teamShortName).longName(teamLongName).build();
        masterDataManagerService.createTeam(team);
        return TeamRef.of(team.getShortName());
    }

    public SeasonRef season(String name, String year, SeasonType type, TeamType teamType) {
        Season season = new Season();
        season.setName(name);
        season.setYear(year);
        season.setTeamType(teamType);
        season.setMode(type);
        Season season1 = seasonManagerService.createSeason(season);
        return SeasonRef.of(season1.getName(), season1.getYear());
    }

    public SeasonRef group(GroupTypeRef groupTypeRef, SeasonRef seasonRef) {
        Optional<GroupType> groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType());
        Optional<Season> season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year());

        if (groupType.isPresent() && season.isPresent()) {
            seasonManagerService.addGroupType(season.get(), groupType.get());
        }

        return seasonRef;
    }

    public RoundRef round(SeasonRef seasonRef, RoundRef roundRef, LocalDateTime ldt) {
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));

        GroupType groupType = masterDataManagerService.findGroupType(roundRef.groupType().groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found");
            seasonManagerService.addRound(s, ldt, roundRef.groupType());


        return null;
    }

    public LocalDateTime toDate(String dateTimeAsString) {
        ZoneId zone = ZoneId.of("Europe/Berlin");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeAsString, formatter);
    }

}
