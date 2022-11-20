/*
 * ============================================================================
 * Project betoffice-wrapper Copyright (c) 2000-2022 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.wrapper.impl;

import static de.betoffice.wrapper.impl.TryGetCatcher.tryGetCatch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.betoffice.wrapper.api.GameResult;
import de.betoffice.wrapper.api.*;
import de.winkler.betoffice.service.MasterDataManagerService;
import de.winkler.betoffice.service.SeasonManagerService;
import de.winkler.betoffice.storage.*;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

/**
 * Default implementation of the Betoffice API.
 *
 * @author Andre Winkler
 */
@Component
public class DefaultBetofficeApi implements BetofficeApi {

    private static final ZoneId ZONE_EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

    @Autowired
    private SeasonManagerService seasonManagerService;

    @Autowired
    private MasterDataManagerService masterDataManagerService;
    
    @Override
    public OperationResult<GroupTypeRef> groupType(String groupTypeName) {
        return tryGetCatch(() -> buildGroupType(groupTypeName));
    }

    private GroupTypeRef buildGroupType(String groupTypeName) {
        GroupType groupType = new GroupType();
        groupType.setName(groupTypeName);
        masterDataManagerService.createGroupType(groupType);
        return GroupTypeRef.of(groupTypeName);
    }

    @Override
    public OperationResult<TeamRef> team(String teamName, String teamLongName) {
        return tryGetCatch(() -> buildTeam(teamName, teamLongName));
    }

    private TeamRef buildTeam(String teamName, String teamLongName) {
        Team team = Team.TeamBuilder.team(teamName).longName(teamLongName).build();
        masterDataManagerService.createTeam(team);
        return TeamRef.of(team.getName());
    }

    @Override
    public OperationResult<SeasonRef> season(String name, String year, SeasonType seasonType, TeamType teamType) {
        return tryGetCatch(() -> buildSeason(name, year, seasonType, teamType));
    }

    private SeasonRef buildSeason(String name, String year, SeasonType type, TeamType teamType) {
        Season season = new Season();
        season.setName(name);
        season.setYear(year);
        season.setTeamType(teamType);
        season.setMode(type);
        Season season1 = seasonManagerService.createSeason(season);
        return SeasonRef.of(season1.getName(), season1.getYear());
    }

    @Override
    public OperationResult<SeasonRef> group(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        return tryGetCatch(() -> buildGroup(seasonRef, groupTypeRef));
    }

    private SeasonRef buildGroup(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> new IllegalArgumentException("groupType not found"));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> new IllegalArgumentException("season not found"));

        seasonManagerService.addGroupType(season, groupType);

        return seasonRef;
    }

	@Override
    public OperationResult<SeasonRef> addTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef) {
        return tryGetCatch(() -> buildAddTeam(seasonRef, groupTypeRef, teamRef));
    }

	private SeasonRef buildAddTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef) {
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
	 * Better use {@link #addRound(SeasonRef, GroupTypeRef, ZonedDateTime)} with a ZonedDateTime.
	 * This method assumes timezone Europe/Berlin.
	 *
	 * @see #addRound(SeasonRef, GroupTypeRef, ZonedDateTime)
	 */
    @Override
    public OperationResult<RoundRef> round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, LocalDateTime ldt) {
        return tryGetCatch(() -> buildRound(seasonRef, groupTypeRef, toZonedDateTime(ldt)));
    }

	@Override
	public OperationResult<RoundRef> addRound(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt) {
        return tryGetCatch(() -> buildRound(seasonRef, groupTypeRef, ldt));
    }

    private RoundRef buildRound(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt) {
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
    public OperationResult<GameRef> game(GameRef gameRef, ZonedDateTime zdt) {
        return tryGetCatch(() -> buildGame(gameRef, zdt));
    }

    private GameRef buildGame(GameRef gameRef, ZonedDateTime zdt) {
        Game game = seasonManagerService.findMatch(gameRef.betofficeId().id());
        game.setDateTime(zdt);
        return gameRef;
    }

    @Override
    public OperationResult<GameRef> addGame(SeasonRef seasonRef, GroupTypeRef groupTypeRef,
                                         RoundIndex roundIndex, ZonedDateTime zdt,
                                         TeamRef homeTeamRef, TeamRef guestTeamRef) {
        return tryGetCatch(() -> buildGame(seasonRef, groupTypeRef, roundIndex, zdt, homeTeamRef, guestTeamRef));
    }

    private GameRef buildGame(SeasonRef seasonRef, GroupTypeRef groupTypeRef,
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
        Game game = seasonManagerService.addMatch(round, zdt, group, homeTeam, guestTeam);

        return GameRef.of(BetofficeId.of(game.getId()), seasonRef, GroupRef.of(seasonRef, groupTypeRef), roundIndex, homeTeamRef, guestTeamRef);
    }

    @Override
    public OperationResult<GameRef> result(GameRef gameRef, Scoring scoring) {
        return tryGetCatch(() -> buildResult(gameRef, null, scoring));
    }

    @Override
    public OperationResult<GameRef> result(GameRef gameRef, ZonedDateTime zdt, Scoring scoring) {
        return tryGetCatch(() -> buildResult(gameRef, zdt, scoring));
    }
    @Override
    public OperationResult<GameRef> result(GameRef gameRef, GameResult halfTimeResult, GameResult result) {
        return tryGetCatch(() -> buildResult(gameRef, null, Scoring.of(halfTimeResult, result)));
    }

    private GameRef buildResult(GameRef gameRef, ZonedDateTime zdt, Scoring scoring) {
        Game match = seasonManagerService.findMatch(gameRef.betofficeId().id());
        if (match == null) {
            throw new IllegalArgumentException(String.format("game with id='%s' not found", gameRef.betofficeId().id()));
        }
        match.setHalfTimeGoals(toGameResult(scoring.getHalfTimeResult()));
        match.setResult(toGameResult(scoring.getResult()));
        match.setOverTimeGoals(toGameResult(scoring.getOvertimeResult()));
        match.setPenaltyGoals(toGameResult(scoring.getPenaltyResult()));
        match.setPlayed(true);
        if (zdt != null) {
            match.setDateTime(zdt);
        }
        seasonManagerService.updateMatch(match);

        return gameRef;
    }

    private static de.winkler.betoffice.storage.GameResult toGameResult(GameResult result) {
        return de.winkler.betoffice.storage.GameResult.of(result.getHomeGoals(), result.getGuestGoals());
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
