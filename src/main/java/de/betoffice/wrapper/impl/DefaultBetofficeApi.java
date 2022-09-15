package de.betoffice.wrapper.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import de.betoffice.wrapper.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.winkler.betoffice.service.MasterDataManagerService;
import de.winkler.betoffice.service.SeasonManagerService;
import de.winkler.betoffice.storage.GameList;
import de.winkler.betoffice.storage.Group;
import de.winkler.betoffice.storage.GroupType;
import de.winkler.betoffice.storage.Season;
import de.winkler.betoffice.storage.Team;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

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
    public TeamRef team(String teamName, String teamLongName) {
        Team team = Team.TeamBuilder.team(teamName).longName(teamLongName).build();
        masterDataManagerService.createTeam(team);
        return TeamRef.of(team.getName());
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
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found"));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));

        seasonManagerService.addGroupType(season, groupType);

        return seasonRef;
    }

	@Override
	public SeasonRef addTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found"));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));
        Team team = masterDataManagerService.findTeam(teamRef.name())
                .orElseThrow(() -> new IllegalArgumentException("team not found"));

        seasonManagerService.addTeam(season, groupType, team);

		return seasonRef;
	}

	/**
	 * Better use {@link #round(SeasonRef, GroupTypeRef, ZonedDateTime)} with a ZonedDateTime.
	 * This method assumes timezone Europe/Berlin.
	 *
	 * @see #round(SeasonRef, GroupTypeRef, ZonedDateTime)
	 */
    @Override
    public RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, LocalDateTime ldt) {
    	return round(seasonRef, groupTypeRef, toZonedDateTime(ldt));
    }

	@Override
	public RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found"));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));

        GameList gameList = seasonManagerService.addRound(season, ldt, groupType);

        RoundIndex roundIndex = RoundIndex.of(gameList.getIndex() + 1);
        RoundRef roundRef = RoundRef.of(seasonRef, roundIndex, groupTypeRef);

        return roundRef;		
	}

    @Override
    public GameRef game(SeasonRef seasonRef, GroupTypeRef groupTypeRef,
                        RoundIndex roundIndex, ZonedDateTime zdt,
                        TeamRef homeTeamRef, TeamRef guestTeamRef) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found"));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));
        Group group = seasonManagerService.findGroup(season, groupType);
        Team homeTeam = masterDataManagerService.findTeam(homeTeamRef.name())
                .orElseThrow(() -> new IllegalArgumentException("homeTeam not found"));
        Team guestTeam = masterDataManagerService.findTeam(guestTeamRef.name())
                .orElseThrow(() -> new IllegalArgumentException("guestTeam not found"));

        GameList round = seasonManagerService.findRound(season, roundIndex.betofficeIndex())
                .orElseThrow(() -> new IllegalArgumentException("round not found"));
        seasonManagerService.addMatch(round, zdt, group, homeTeam, guestTeam);

        return GameRef.of(seasonRef, GroupRef.of(seasonRef, groupTypeRef), roundIndex, homeTeamRef, guestTeamRef);
    }

    @Override
    public GameRef updateGame(GameRef gameRef, ZonedDateTime zdt,
                              GameResult halfTimeResult, GameResult result,
                              GameResult overtimeResult, GameResult penaltyResult) {

        Season season = seasonManagerService.findSeasonByName(gameRef.getSeason().name(), gameRef.getSeason().year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));
        GameList round = seasonManagerService.findRound(season, gameRef.getRound().betofficeIndex())
                .orElseThrow(() -> new IllegalArgumentException("round not found"));

        seasonManagerService.findMatch

        return null;
    }

    @Override
    public GameRef updateGame(GameRef gameRef, GameResult halfTimeResult,
                              GameResult result, GameResult overtimeResult,
                              GameResult penaltyResult) {
        return null;
    }

    @Override
    public GameRef updateGame(GameRef gameRef, ZonedDateTime zdt) {
        return null;
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
