package de.betoffice.wrapper.impl;

import de.betoffice.wrapper.api.*;
import de.winkler.betoffice.storage.GameList;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class DefaultBetofficeApi implements BetofficeApi {

    private static final ZoneId ZONE_EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

    @Autowired
    private SeasonManagerService seasonManagerService;

    @Autowired
    private MasterDataManagerService masterDataManagerService;
    
    @Override
    public GroupTypeRef groupType(String groupTypeName) {
        GroupType groupType = new GroupType();
        groupType.setName(groupTypeName);
        masterDataManagerService.createGroupType(groupType);
        return GroupTypeRef.of(groupType.getName());
    }

    @Override
    public TeamRef team(String teamShortName, String teamLongName) {
        Team team = Team.TeamBuilder.team(teamShortName).longName(teamLongName).build();
        masterDataManagerService.createTeam(team);
        return TeamRef.of(team.getShortName());
    }

    @Override
    public SeasonRef season(String name, String year, SeasonType type, TeamType teamType) {
        Season season = new Season();
        season.setName(name);
        season.setYear(year);
        season.setTeamType(teamType);
        season.setMode(type);
        Season season1 = seasonManagerService.createSeason(season);
        return SeasonRef.of(season1.getName(), season1.getYear());
    }

    @Override
    public SeasonRef group(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        Optional<GroupType> groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType());
        Optional<Season> season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year());

        if (groupType.isPresent() && season.isPresent()) {
            seasonManagerService.addGroupType(season.get(), groupType.get());
        }

        return seasonRef;
    }

	@Override
	public SeasonRef addTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef) {
        Optional<GroupType> groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType());
        Optional<Season> season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year());
        Optional<Team> team = masterDataManagerService.findTeam(teamRef.shortName());

        if (groupType.isPresent() && season.isPresent() && team.isPresent()) {
        	seasonManagerService.addTeam(season.get(), groupType.get(), team.get());
        }

		return seasonRef;
	}

    @Override
    public RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, LocalDateTime ldt) {
    	return round(seasonRef, groupTypeRef, toZonedDateTime(ldt));
    }

	@Override
	public RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt) {
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));

        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found"));

        GameList gameList = seasonManagerService.addRound(season, ldt, groupType);

        RoundIndex roundIndex = RoundIndex.of(gameList.getIndex() + 1);
        RoundRef roundRef = RoundRef.of(seasonRef, roundIndex, groupTypeRef);

        return roundRef;		
	}

    @Override
    public ZonedDateTime toZonedDateTime(LocalDateTime ldt) {
        return ldt.atZone(ZONE_EUROPE_BERLIN);
    }

    @Override
    public LocalDateTime toDate(String dateTimeAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeAsString, formatter);
    }

}
