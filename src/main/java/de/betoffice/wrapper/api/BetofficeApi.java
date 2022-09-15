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

package de.betoffice.wrapper.api;

import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * The BETOFFICE API.
 */
public interface BetofficeApi {

    GroupTypeRef groupType(String groupTypeName);

    TeamRef team(String teamShortName, String teamLongName);

    SeasonRef season(String name, String year, SeasonType type, TeamType teamType);

    SeasonRef group(SeasonRef seasonRef, GroupTypeRef groupTypeRef);
    
    SeasonRef addTeam(SeasonRef seasonRef, GroupTypeRef groupTypeRef, TeamRef teamRef);

    RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, LocalDateTime ldt);
    
    RoundRef round(SeasonRef seasonRef, GroupTypeRef groupTypeRef, ZonedDateTime ldt);
    
    GameRef game(SeasonRef season, GroupTypeRef groupTypeRef,
                 RoundIndex roundIndex, ZonedDateTime zdt,
                 TeamRef homeTeam, TeamRef guestTeam);

    GameRef updateGame(GameRef gameRef, ZonedDateTime zdt,
                       GameResult halfTimeResult, GameResult result,
                       GameResult overtimeResult, GameResult penaltyResult);

    GameRef updateGame(GameRef gameRef, GameResult halfTimeResult,
                       GameResult result, GameResult overtimeResult,
                       GameResult penaltyResult);

    GameRef updateGame(GameRef gameRef, ZonedDateTime zdt);
    
    ZonedDateTime toZonedDateTime(LocalDateTime ldt);

    LocalDateTime toDate(String dateTimeAsString);

}
