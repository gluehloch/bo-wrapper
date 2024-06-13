package de.betoffice.wrapper.cli;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.betoffice.openligadb.OpenligadbUpdateService;
import de.betoffice.wrapper.api.BetofficeApi;
import de.betoffice.wrapper.api.RoundRef;
import de.betoffice.wrapper.api.SeasonRef;
import de.betoffice.wrapper.data.BetofficeData;
import de.winkler.betoffice.service.CommunityService;
import de.winkler.betoffice.storage.Community;
import de.winkler.betoffice.storage.CommunityReference;
import de.winkler.betoffice.storage.Nickname;
import de.winkler.betoffice.storage.SeasonReference;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

@Service
@Transactional
public class EM2024Setup {

    private final BetofficeApi api;
    private final OpenligadbUpdateService openDbService;
    private final CommunityService communityService;

    public EM2024Setup(BetofficeApi betofficeApi, OpenligadbUpdateService opendDbService,
            CommunityService communityService) {
        this.api = betofficeApi;
        this.openDbService = opendDbService;
        this.communityService = communityService;
    }

    public void createGeorgien() {
        api.createTeam("Georgien", "Georgien", TeamType.FIFA);
    }

    public void setupEM2024Vorrunde() {
        SeasonRef seasonRef = api.createSeason("EM Deutschland", "2024", SeasonType.EC, TeamType.FIFA, "em", "2024")
                .orThrow();
        api.addGroup(seasonRef, BetofficeData.REF_GRUPPE_A).orThrow();
        api.addGroup(seasonRef, BetofficeData.REF_GRUPPE_B).orThrow();
        api.addGroup(seasonRef, BetofficeData.REF_GRUPPE_C).orThrow();
        api.addGroup(seasonRef, BetofficeData.REF_GRUPPE_D).orThrow();
        api.addGroup(seasonRef, BetofficeData.REF_GRUPPE_E).orThrow();
        api.addGroup(seasonRef, BetofficeData.REF_GRUPPE_F).orThrow();

        // Gruppe A
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_DEUTSCHLAND).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_SCHOTTLAND).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_SCHWEIZ).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_UNGARN).orThrow();

        // Gruppe B
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_ALBANIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_ITALIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_KROATIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_SPANIEN).orThrow();

        // Gruppe C
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_C, BetofficeData.REF_DAENEMARK).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_C, BetofficeData.REF_ENGLAND).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_C, BetofficeData.REF_SERBIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_C, BetofficeData.REF_SLOWENIEN).orThrow();

        // Gruppe D
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_D, BetofficeData.REF_FRANKREICH).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_D, BetofficeData.REF_NIEDERLANDE).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_D, BetofficeData.REF_OESTERREICH).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_D, BetofficeData.REF_POLEN).orThrow();

        // Gruppe E
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_E, BetofficeData.REF_BELGIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_E, BetofficeData.REF_RUMAENIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_E, BetofficeData.REF_SLOWAKEI).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_E, BetofficeData.REF_UKRAINE).orThrow();

        // Gruppe F
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_F, BetofficeData.REF_GEORGIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_F, BetofficeData.REF_PORTUGAL).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_F, BetofficeData.REF_TSCHECHIEN).orThrow();
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_F, BetofficeData.REF_TUERKEI).orThrow();

        // Runde 1 / Gruppenspieltag 1 / TODO vs 'Gruppe A'
        RoundRef runde1 = api.addRound(seasonRef, BetofficeData.REF_GRUPPE_A, LocalDateTime.of(2024, 6, 14, 21, 0))
                .orThrow();
        RoundRef runde2 = api.addRound(seasonRef, BetofficeData.REF_GRUPPE_A, LocalDateTime.of(2024, 6, 19, 15, 0))
                .orThrow();
        RoundRef runde3 = api.addRound(seasonRef, BetofficeData.REF_GRUPPE_A, LocalDateTime.of(2024, 6, 23, 21, 0))
                .orThrow();

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, runde1.index(),
                ZonedDateTime.of(2024, 6, 14, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_DEUTSCHLAND, BetofficeData.REF_SCHOTTLAND);

        // TODO
        openDbService.createOrUpdateRound(36, 0);
        CommunityReference communityReference = CommunityReference.of("TDKB 2024");
        Set<Nickname> nicknames = Set.of(
                Nickname.of("Frosch"),
                Nickname.of("Steffen"),
                Nickname.of("mrTipp"),
                Nickname.of("Jogi"),
                Nickname.of("Peter"),
                Nickname.of("Martin04"),
                Nickname.of("Mao"),
                Nickname.of("Svea"),
                Nickname.of("chris"));
        SeasonReference season = SeasonReference.of(seasonRef.year(), seasonRef.name());
        Community community = communityService
                .create(communityReference, season, "EM Deutschland 2024", Nickname.of("Frosch")).orElseThrow();
        communityService.addMembers(communityReference, nicknames);
    }

}
