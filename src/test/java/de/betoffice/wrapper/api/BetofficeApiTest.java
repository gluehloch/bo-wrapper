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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.betoffice.database.data.DeleteDatabase;
import de.winkler.betoffice.conf.PersistenceJPAConfiguration;
import de.winkler.betoffice.conf.TestPropertiesConfiguration;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceJPAConfiguration.class, TestPropertiesConfiguration.class })
@ComponentScan({"de.winkler.betoffice", "de.betoffice"})
class BetofficeApiTest {

    private static final ZonedDateTime DATE_2010_09_15 = ZonedDateTime
            .of(LocalDateTime.of(LocalDate.of(2010, 9, 15), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));
    private static final ZonedDateTime DATE_2010_09_08 = ZonedDateTime
            .of(LocalDateTime.of(LocalDate.of(2010, 9, 8), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));
    private static final ZonedDateTime DATE_2010_09_01 = ZonedDateTime
            .of(LocalDateTime.of(LocalDate.of(2010, 9, 9), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));

    @Autowired
    private BetofficeApi betofficeApi;

    @Autowired
    protected DataSource dataSource;

    @BeforeEach
    public void setUp() throws Exception {
        cleanUpDatabase();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        cleanUpDatabase();
    }

    private void cleanUpDatabase() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            DeleteDatabase.deleteDatabase(conn);
        }
    }

    @Test
    void betofficeApi() throws Throwable {
        final GroupTypeRef bundesliga_1 = betofficeApi.createGroupType("1. Bundesliga").orThrow();

        final TeamRef rwe = betofficeApi.createTeam("RWE", "Rot-Weiss-Essen", TeamType.DFB).orThrow();
        final TeamRef schalke = betofficeApi.createTeam("S04", "Schalke 04", TeamType.DFB).orThrow();
        final TeamRef burghausen = betofficeApi.createTeam("Wacker", "Wacker Burghausen", TeamType.DFB).orThrow();
        final TeamRef hsv = betofficeApi.createTeam("HSV", "Hamburger SV", TeamType.DFB).orThrow();

        final SeasonRef buli_2010 = betofficeApi
                .createSeason("Bundesliga 2010/2011", "2010/2011", SeasonType.LEAGUE, TeamType.DFB).orThrow();
        betofficeApi.addGroup(buli_2010, bundesliga_1);

        betofficeApi.addTeam(buli_2010, bundesliga_1, hsv);
        betofficeApi.addTeam(buli_2010, bundesliga_1, schalke);
        betofficeApi.addTeam(buli_2010, bundesliga_1, burghausen);
        betofficeApi.addTeam(buli_2010, bundesliga_1, rwe);

        assertThat(betofficeApi.addTeam(buli_2010, bundesliga_1, rwe).success()).isFalse();

        final RoundRef round1 = betofficeApi.addRound(buli_2010, bundesliga_1, DATE_2010_09_01).orThrow();
        assertThat(round1.index().betofficeIndex()).isZero();

        final RoundRef round2 = betofficeApi.addRound(buli_2010, bundesliga_1, DATE_2010_09_08).orThrow();
        assertThat(round2.index().betofficeIndex()).isEqualTo(1);

        final RoundRef round3 = betofficeApi.addRound(buli_2010, bundesliga_1, DATE_2010_09_15).orThrow();
        assertThat(round3.index().betofficeIndex()).isEqualTo(2);

        final GameRef rweVsSchalke = betofficeApi
                .createGame(buli_2010, bundesliga_1, round1.index(), DATE_2010_09_01, rwe, schalke).orThrow();
        assertThat(rweVsSchalke.getHomeTeam()).isEqualTo(rwe);
        assertThat(rweVsSchalke.getGuestTeam()).isEqualTo(schalke);
        assertThat(rweVsSchalke.getRound().betofficeIndex()).isEqualTo(round1.index().betofficeIndex());
        assertThat(rweVsSchalke.getGroup().groupType()).isEqualTo(bundesliga_1);

        final GameRef burghausenVsHsv = betofficeApi
                .createGame(buli_2010, bundesliga_1, round1.index(), DATE_2010_09_01, burghausen, hsv).orThrow();
        assertThat(burghausenVsHsv.getHomeTeam()).isEqualTo(burghausen);
        assertThat(burghausenVsHsv.getGuestTeam()).isEqualTo(hsv);
        assertThat(burghausenVsHsv.getRound().betofficeIndex()).isEqualTo(round1.index().betofficeIndex());
        assertThat(burghausenVsHsv.getGroup().groupType()).isEqualTo(bundesliga_1);

        assertThat(betofficeApi.updateGame(rweVsSchalke, GameResult.of(0, 0), GameResult.of(0, 0)).success()).isTrue();
    }

}
