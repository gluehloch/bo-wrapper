/*
 * ============================================================================
 * Project betoffice-wrapper Copyright (c) 2000-2024 by Andre Winkler. All
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
import java.util.List;

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

    private enum TeamHomeOrGuest {
        HOME, GUEST
    }

    private static final ZoneId ZONE_EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

    @Autowired
    private SeasonManagerService seasonManagerService;

    @Autowired
    private MasterDataManagerService masterDataManagerService;

    @Override
    public ApiResult<List<GroupTypeRef>> groupTypes() {
        return tryGetCatch(() -> findAllGroupTypes());
    }

    private List<GroupTypeRef> findAllGroupTypes() {
        return masterDataManagerService.findAllGroupTypes().stream().map(DefaultBetofficeApi::of).toList();
    }

    private static GroupTypeRef of(GroupType groupType) {
        return GroupTypeRef.of(groupType.getName());
    }

    @Override
    public ApiResult<GroupTypeRef> createGroupType(String groupTypeName) {
        return tryGetCatch(() -> buildGroupType(groupTypeName));
    }

    private GroupTypeRef buildGroupType(String groupTypeName) {
        GroupType groupType = new GroupType();
        groupType.setName(groupTypeName);
        masterDataManagerService.createGroupType(groupType);
        return GroupTypeRef.of(groupTypeName);
    }

    @Override
    public ApiResult<TeamRef> createTeam(String teamName, String teamLongName, TeamType teamType) {
        return tryGetCatch(() -> buildTeam(teamName, teamLongName, teamType));
    }

    private TeamRef buildTeam(String teamName, String teamLongName, TeamType teamType) {
        Team team = Team.TeamBuilder.team(teamName).longName(teamLongName).type(teamType).build();
        masterDataManagerService.createTeam(team);
        return TeamRef.of(team.getName());
    }

    @Override
    public ApiResult<SeasonRef> createSeason(String name, String year, SeasonType seasonType, TeamType teamType,
            String opldbShortCut, String opldpSeason) {
        return tryGetCatch(() -> buildSeason(name, year, seasonType, teamType, opldbShortCut, opldpSeason));
    }

    private SeasonRef buildSeason(String name, String year, SeasonType type, TeamType teamType, String opldbShortCut, String opldpSeason) {
        Season season = new Season();
        season.setReference(SeasonReference.of(year, name));
        season.setTeamType(teamType);
        season.setMode(type);
        season.getChampionshipConfiguration().setOpenligaLeagueShortcut(opldbShortCut);
        season.getChampionshipConfiguration().setOpenligaLeagueSeason(opldpSeason);
        Season season1 = seasonManagerService.createSeason(season);
        return SeasonRef.of(season1.getReference().getName(), season1.getReference().getYear());
    }

    @Override
    public ApiResult<SeasonRef> addGroup(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        return tryGetCatch(() -> buildGroup(seasonRef, groupTypeRef));
    }

    private SeasonRef buildGroup(SeasonRef seasonRef, GroupTypeRef groupTypeRef) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> groupTypeRefNotFound(groupTypeRef));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> seasonNotFound(seasonRef));

        seasonManagerService.addGroupType(season, groupType);

        return seasonRef;
    }

    @Override
    public ApiResult<SeasonRef> addTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef) {
        return tryGetCatch(() -> buildAddTeam(seasonRef, groupTypeRef, teamRef));
    }

    private SeasonRef buildAddTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> groupTypeRefNotFound(groupTypeRef));

        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> seasonNotFound(seasonRef));
        Team team = masterDataManagerService.findTeam(teamRef.name())
                .orElseThrow(() -> teamNotFound(teamRef));

        seasonManagerService.addTeam(season, groupType, team);

        return seasonRef;
    }

    /**
     * Better use {@link #addRound(SeasonRef, GroupTypeRef, ZonedDateTime)} with a
     * ZonedDateTime.
     * This method assumes timezone Europe/Berlin.
     *
     * @see #addRound(SeasonRef, GroupTypeRef, ZonedDateTime)
     */
    @Override
    public ApiResult<RoundRef> addRound(SeasonRef seasonRef, GroupTypeRef groupTypeRef, LocalDateTime ldt) {
        return tryGetCatch(() -> buildRound(seasonRef, groupTypeRef, toZonedDateTime(ldt)));
    }

    @Override
    public ApiResult<RoundRef> addRound(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt) {
        return tryGetCatch(() -> buildRound(seasonRef, groupTypeRef, ldt));
    }

    private RoundRef buildRound(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> groupTypeRefNotFound(groupTypeRef));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> seasonNotFound(seasonRef));

        GameList gameList = seasonManagerService.addRound(season, ldt, groupType);

        RoundIndex roundIndex = RoundIndex.of(gameList.getIndex() + 1);
        RoundRef roundRef = RoundRef.of(seasonRef, roundIndex, groupTypeRef);

        return roundRef;
    }

    @Override
    public ApiResult<GameRef> updateGame(GameRef gameRef, ZonedDateTime zdt) {
        return tryGetCatch(() -> buildGame(gameRef, zdt));
    }

    private GameRef buildGame(GameRef gameRef, ZonedDateTime zdt) {
        Game game = seasonManagerService.findMatch(gameRef.betofficeId().id());
        game.setDateTime(zdt);
        return gameRef;
    }

    @Override
    public ApiResult<GameRef> createGame(SeasonRef seasonRef, GroupTypeRef groupTypeRef,
            RoundIndex roundIndex, ZonedDateTime zdt,
            TeamRef homeTeamRef, TeamRef guestTeamRef) {
        return tryGetCatch(() -> buildGame(seasonRef, groupTypeRef, roundIndex, zdt, homeTeamRef, guestTeamRef));
    }

    private GameRef buildGame(SeasonRef seasonRef, GroupTypeRef groupTypeRef,
            RoundIndex roundIndex, ZonedDateTime zdt,
            TeamRef homeTeamRef, TeamRef guestTeamRef) {
        GroupType groupType = masterDataManagerService.findGroupType(groupTypeRef.groupType())
                .orElseThrow(() -> groupTypeRefNotFound(groupTypeRef));
        Season season = seasonManagerService.findSeasonByName(seasonRef.name(), seasonRef.year())
                .orElseThrow(() -> seasonNotFound(seasonRef));
        Group group = seasonManagerService.findGroup(season, groupType);
        Team homeTeam = masterDataManagerService.findTeam(homeTeamRef.name())
                .orElseThrow(() -> teamNotFound(homeTeamRef, TeamHomeOrGuest.HOME));
        Team guestTeam = masterDataManagerService.findTeam(guestTeamRef.name())
                .orElseThrow(() -> teamNotFound(guestTeamRef, TeamHomeOrGuest.GUEST));

        RoundRef roundRef = RoundRef.of(seasonRef, roundIndex, groupTypeRef);
        GameList round = seasonManagerService.findRound(season, roundIndex.betofficeIndex())
                .orElseThrow(() -> roundNotFound(roundRef));
        Game game = seasonManagerService.addMatch(round, zdt, group, homeTeam, guestTeam);

        return GameRef.of(BetofficeId.of(game.getId()), seasonRef, GroupRef.of(seasonRef, groupTypeRef), roundIndex,
                homeTeamRef, guestTeamRef);
    }

    @Override
    public ApiResult<GameRef> updateGame(GameRef gameRef, Scoring scoring) {
        return tryGetCatch(() -> buildResult(gameRef, null, scoring));
    }

    @Override
    public ApiResult<GameRef> updateGame(GameRef gameRef, ZonedDateTime zdt, Scoring scoring) {
        return tryGetCatch(() -> buildResult(gameRef, zdt, scoring));
    }

    @Override
    public ApiResult<GameRef> updateGame(GameRef gameRef, GameResult halfTimeResult, GameResult result) {
        return tryGetCatch(() -> buildResult(gameRef, null, Scoring.of(halfTimeResult, result)));
    }

    private GameRef buildResult(GameRef gameRef, ZonedDateTime zdt, Scoring scoring) {
        Game match = seasonManagerService.findMatch(gameRef.betofficeId().id());
        if (match == null) {
            throw new IllegalArgumentException(
                    String.format("game with id='%s' not found", gameRef.betofficeId().id()));
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

    private static RuntimeException groupTypeRefNotFound(GroupTypeRef groupTypeRef) {
        return new IllegalArgumentException("groupType not found: %s".formatted(groupTypeRef));
    }

    private static RuntimeException seasonNotFound(SeasonRef seasonRef) {
        return new IllegalArgumentException("season not found: %s".formatted(seasonRef));
    }

    private static RuntimeException teamNotFound(TeamRef teamRef) {
        return new IllegalArgumentException("team not found: %s".formatted(teamRef));
    }

    private static RuntimeException teamNotFound(TeamRef teamRef, TeamHomeOrGuest homeOrGuest) {
        switch (homeOrGuest) {
            case HOME:
                return new IllegalArgumentException("home team not found: %s".formatted(teamRef));
            case GUEST:
                new IllegalArgumentException("guest team not found: %s".formatted(teamRef));
        }
        return new IllegalArgumentException("homeOrGuest type is unknown!");
    }

    private static RuntimeException roundNotFound(RoundRef roundRef) {
        return new IllegalArgumentException("round not found: " + roundRef);
    }

}
