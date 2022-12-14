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

import java.util.Objects;

public class GameRef {

	private final BetofficeId id;

	private final SeasonRef seasonRef;
	private final RoundIndex roundIndex;
	private final GroupRef groupRef;

	private final TeamRef homeTeam;
	private final TeamRef guestTeam;

	private GameRef(BetofficeId id, SeasonRef seasonRef, GroupRef groupRef, RoundIndex roundIndex,
					TeamRef homeTeam, TeamRef guestTeam) {

		this.id = id;
		this.seasonRef = seasonRef;
		this.groupRef = groupRef;
		this.roundIndex = roundIndex;
		this.homeTeam = homeTeam;
		this.guestTeam = guestTeam;
	}

	public static GameRef of(BetofficeId id, SeasonRef seasonRef, GroupRef groupRef, RoundIndex roundIndex,
							 TeamRef homeTeam, TeamRef guestTeam) {

		Objects.requireNonNull(id);
		Objects.requireNonNull(seasonRef);
		Objects.requireNonNull(roundIndex);
		Objects.requireNonNull(groupRef);
		Objects.requireNonNull(homeTeam);
		Objects.requireNonNull(guestTeam);
		return new GameRef(id, seasonRef, groupRef, roundIndex, homeTeam, guestTeam);
	}

	public BetofficeId betofficeId() {
		return id;
	}

	public TeamRef getHomeTeam() {
		return homeTeam;
	}

	public TeamRef getGuestTeam() {
		return guestTeam;
	}

	public SeasonRef getSeason() {
		return seasonRef;
	}

	public GroupRef getGroup() {
		return groupRef;
	}

	public RoundIndex getRound() {
		return roundIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GameRef gameRef = (GameRef) o;
		return Objects.equals(seasonRef, gameRef.seasonRef)
				&& Objects.equals(roundIndex, gameRef.roundIndex)
				&& Objects.equals(groupRef, gameRef.groupRef)
				&& Objects.equals(homeTeam, gameRef.homeTeam)
				&& Objects.equals(guestTeam, gameRef.guestTeam);
	}

	@Override
	public int hashCode() {
		return Objects.hash(seasonRef, roundIndex, groupRef, homeTeam, guestTeam);
	}

}
