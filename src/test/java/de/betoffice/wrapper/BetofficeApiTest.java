package de.betoffice.wrapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.sql.DataSource;

import de.betoffice.wrapper.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import de.betoffice.database.data.MySqlDatabasedTestSupport.DataLoader;
import de.winkler.betoffice.service.DatabaseMaintenanceService;
import de.winkler.betoffice.service.MasterDataManagerService;
import de.winkler.betoffice.service.SeasonManagerService;
import de.winkler.betoffice.service.TippService;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

@SpringJUnitConfig(locations = { "/betoffice-test-properties.xml", "/betoffice.xml" })
public class BetofficeApiTest {

	private static final ZonedDateTime DATE_15_09_2010 = ZonedDateTime
			.of(LocalDateTime.of(LocalDate.of(2010, 9, 15), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));
	private static final ZonedDateTime DATE_08_09_2010 = ZonedDateTime
			.of(LocalDateTime.of(LocalDate.of(2010, 9, 8), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));
	private static final ZonedDateTime DATE_01_09_2010 = ZonedDateTime
			.of(LocalDateTime.of(LocalDate.of(2010, 9, 9), LocalTime.of(0, 0)), ZoneId.of("Europe/Berlin"));

	private TeamRef rwe;
	private TeamRef schalke;
	private TeamRef burghausen;
	private TeamRef hsv;

	private GroupTypeRef bundesliga_1;

	private SeasonRef buli_2010;

	@Autowired
	private BetofficeApi betofficeApi;
	
    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected SeasonManagerService seasonManagerService;

    @Autowired
    protected TippService tippService;

    @Autowired
    protected MasterDataManagerService masterDataManagerService;

    @Autowired
    protected DatabaseMaintenanceService databaseMaintenanceService;

    private DatabaseSetUpAndTearDown dsuatd;

    @BeforeEach
    public void setUp() throws Exception {
        dsuatd = new DatabaseSetUpAndTearDown(dataSource);
        dsuatd.tearDown();
        dsuatd.setUp(DataLoader.EMPTY);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        dsuatd.tearDown();
    }

	@Test
	void updateMatchDay() {
		createSeason();
	}

	private SeasonRef createSeason() {
		bundesliga_1 = betofficeApi.groupType("1. Bundesliga");

		rwe = betofficeApi.team("RWE", "Rot-Weiss-Essen");
		schalke = betofficeApi.team("S04", "Schalke 04");
		burghausen = betofficeApi.team("Wacker", "Wacker Burghausen");
		hsv = betofficeApi.team("HSV", "Hamburger SV");
		
		buli_2010 = betofficeApi.season("Bundesliga 2010/2011", "2010/2011", SeasonType.LEAGUE, TeamType.DFB);

    	betofficeApi.group(buli_2010, bundesliga_1);

        betofficeApi.addTeam(buli_2010, bundesliga_1, hsv);
        betofficeApi.addTeam(buli_2010, bundesliga_1, schalke);
        betofficeApi.addTeam(buli_2010, bundesliga_1, burghausen);
        betofficeApi.addTeam(buli_2010, bundesliga_1, rwe);
        
        buli_2010 = betofficeApi.addTeam(buli_2010, bundesliga_1,  rwe);

		RoundRef roundRef = betofficeApi.round(buli_2010, bundesliga_1, DATE_01_09_2010);
		RoundRef roundRef1 = betofficeApi.round(buli_2010, bundesliga_1, DATE_08_09_2010);
		RoundRef roundRef2 = betofficeApi.round(buli_2010, bundesliga_1, DATE_15_09_2010);

		betofficeApi.game(buli_2010, bundesliga_1, roundRef.index(), DATE_01_09_2010, rwe, schalke);
		betofficeApi.game(buli_2010, bundesliga_1, roundRef.index(), DATE_01_09_2010, burghausen, hsv);
		
		return buli_2010;
	}

}
