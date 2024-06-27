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

    public void setup() {
        SeasonRef season = SeasonRef.of("EM Deutschland", "2024");
//        RoundRef round1 = RoundRef.of(season, RoundIndex.of(1), BetofficeData.REF_GRUPPE_A);
//        RoundRef round2 = RoundRef.of(season, RoundIndex.of(2), BetofficeData.REF_GRUPPE_A);
//        RoundRef round3 = RoundRef.of(season, RoundIndex.of(3), BetofficeData.REF_GRUPPE_A);
        em2024Achtelfinale(season);
    }

    private void em2024Achtelfinale(SeasonRef seasonRef) {
        RoundRef achtelfinale = api
                .addRound(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, LocalDateTime.of(2024, 6, 29, 18, 0))
                .orThrow();

        // 2024-06-29
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 6, 29, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SCHWEIZ, BetofficeData.REF_ITALIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 6, 29, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_DEUTSCHLAND, BetofficeData.REF_DAENEMARK);

        // 2024-06-30
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 6, 30, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_ENGLAND, BetofficeData.REF_SLOWAKEI);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 6, 30, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SPANIEN, BetofficeData.REF_GEORGIEN);

        // 2024-07-01
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 7, 1, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_FRANKREICH, BetofficeData.REF_BELGIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 7, 1, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_PORTUGAL, BetofficeData.REF_SLOWENIEN);

        // 2024-07-02
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 7, 2, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_RUMAENIEN, BetofficeData.REF_NIEDERLANDE);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_ACHTELFINALE, achtelfinale.index(),
                ZonedDateTime.of(2024, 7, 2, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_OESTERREICH, BetofficeData.REF_TUERKEI);
    }

    public void createGeorgien() {
        api.createTeam("Georgien", "Georgien", TeamType.FIFA);
    }

    private void em2024DritterSpieltag(SeasonRef seasonRef, RoundRef round3) {
        // 2024-06-23
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, round3.index(),
                ZonedDateTime.of(2024, 6, 23, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SCHWEIZ, BetofficeData.REF_DEUTSCHLAND);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, round3.index(),
                ZonedDateTime.of(2024, 6, 23, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SCHOTTLAND, BetofficeData.REF_UNGARN);

        // 2024-06-24
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_B, round3.index(),
                ZonedDateTime.of(2024, 6, 24, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_ALBANIEN, BetofficeData.REF_SPANIEN);
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_B, round3.index(),
                ZonedDateTime.of(2024, 6, 24, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_KROATIEN, BetofficeData.REF_ITALIEN);

        // 2024-06-25
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_D, round3.index(),
                ZonedDateTime.of(2024, 6, 25, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_FRANKREICH, BetofficeData.REF_POLEN);
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_D, round3.index(),
                ZonedDateTime.of(2024, 6, 25, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_NIEDERLANDE, BetofficeData.REF_OESTERREICH);

        // 2024-06-25
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_C, round3.index(),
                ZonedDateTime.of(2024, 6, 25, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_ENGLAND, BetofficeData.REF_SLOWENIEN);
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_C, round3.index(),
                ZonedDateTime.of(2024, 6, 25, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_DAENEMARK, BetofficeData.REF_SERBIEN);

        // 2024-06-26
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_E, round3.index(),
                ZonedDateTime.of(2024, 6, 26, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_UKRAINE, BetofficeData.REF_BELGIEN);
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_E, round3.index(),
                ZonedDateTime.of(2024, 6, 26, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SLOWAKEI, BetofficeData.REF_RUMAENIEN);
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_F, round3.index(),
                ZonedDateTime.of(2024, 6, 26, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_TSCHECHIEN, BetofficeData.REF_TUERKEI);
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_F, round3.index(),
                ZonedDateTime.of(2024, 6, 26, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_GEORGIEN, BetofficeData.REF_PORTUGAL);
    }

    private void setupEM2024Vorrunde() {
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

        em2024ErsterSpieltag(seasonRef, runde1);

        // TODO
        openDbService.createOrUpdateRound(36, 0);

        CommunityReference communityReference = CommunityReference.of("TDKB 2024");
        Set<Nickname> nicknames = Set.of(
                Nickname.of("Frosch"),
                Nickname.of("Steffen"),
                Nickname.of("mrTipp"),
                Nickname.of("Jogi"),
                Nickname.of("Peter"),
                // Nickname.of("Martin04"),
                Nickname.of("Mao"),
                Nickname.of("Svea"),
                Nickname.of("chris"));
        SeasonReference season = SeasonReference.of(seasonRef.year(), seasonRef.name());
        Community community = communityService
                .create(communityReference, season, "EM Deutschland 2024", Nickname.of("Frosch")).orElseThrow();
        communityService.addMembers(communityReference, nicknames);
    }

    private void em2024ZweiterSpieltag(SeasonRef seasonRef, RoundRef runde2) {
        // 2024-06-19
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_B, runde2.index(),
                ZonedDateTime.of(2024, 6, 19, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_KROATIEN, BetofficeData.REF_ALBANIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, runde2.index(),
                ZonedDateTime.of(2024, 6, 19, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_DEUTSCHLAND, BetofficeData.REF_UNGARN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, runde2.index(),
                ZonedDateTime.of(2024, 6, 19, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SCHOTTLAND, BetofficeData.REF_SCHWEIZ);

        // 2024-06-20
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_C, runde2.index(),
                ZonedDateTime.of(2024, 6, 20, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SLOWENIEN, BetofficeData.REF_SERBIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_C, runde2.index(),
                ZonedDateTime.of(2024, 6, 20, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_DAENEMARK, BetofficeData.REF_ENGLAND);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_B, runde2.index(),
                ZonedDateTime.of(2024, 6, 20, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SPANIEN, BetofficeData.REF_ITALIEN);

        // 2024-06-21
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_E, runde2.index(),
                ZonedDateTime.of(2024, 6, 21, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SLOWAKEI, BetofficeData.REF_UKRAINE);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_D, runde2.index(),
                ZonedDateTime.of(2024, 6, 21, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_POLEN, BetofficeData.REF_OESTERREICH);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_D, runde2.index(),
                ZonedDateTime.of(2024, 6, 21, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_NIEDERLANDE, BetofficeData.REF_FRANKREICH);

        // 2024-06-22
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_F, runde2.index(),
                ZonedDateTime.of(2024, 6, 22, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_GEORGIEN, BetofficeData.REF_TSCHECHIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_F, runde2.index(),
                ZonedDateTime.of(2024, 6, 22, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_TUERKEI, BetofficeData.REF_PORTUGAL);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_E, runde2.index(),
                ZonedDateTime.of(2024, 6, 22, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_BELGIEN, BetofficeData.REF_RUMAENIEN);
    }

    private void em2024ErsterSpieltag(SeasonRef seasonRef, RoundRef runde1) {
        // 2024-06-15
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, runde1.index(),
                ZonedDateTime.of(2024, 6, 14, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_DEUTSCHLAND, BetofficeData.REF_SCHOTTLAND);

        // 2024-06-15
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_A, runde1.index(),
                ZonedDateTime.of(2024, 6, 15, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_UNGARN, BetofficeData.REF_SCHWEIZ);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_B, runde1.index(),
                ZonedDateTime.of(2024, 6, 15, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SPANIEN, BetofficeData.REF_KROATIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_B, runde1.index(),
                ZonedDateTime.of(2024, 6, 15, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_ITALIEN, BetofficeData.REF_ALBANIEN);

        // 2024-06-16
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_D, runde1.index(),
                ZonedDateTime.of(2024, 6, 16, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_POLEN, BetofficeData.REF_NIEDERLANDE);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_C, runde1.index(),
                ZonedDateTime.of(2024, 6, 16, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SLOWENIEN, BetofficeData.REF_DAENEMARK);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_C, runde1.index(),
                ZonedDateTime.of(2024, 6, 16, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_SERBIEN, BetofficeData.REF_ENGLAND);

        // 2024-06-17
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_E, runde1.index(),
                ZonedDateTime.of(2024, 6, 17, 15, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_RUMAENIEN, BetofficeData.REF_UKRAINE);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_E, runde1.index(),
                ZonedDateTime.of(2024, 6, 17, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_BELGIEN, BetofficeData.REF_SLOWAKEI);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_D, runde1.index(),
                ZonedDateTime.of(2024, 6, 17, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_OESTERREICH, BetofficeData.REF_FRANKREICH);

        // 2024-06-18
        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_F, runde1.index(),
                ZonedDateTime.of(2024, 6, 18, 18, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_TUERKEI, BetofficeData.REF_GEORGIEN);

        api.createGame(seasonRef, BetofficeData.REF_GRUPPE_F, runde1.index(),
                ZonedDateTime.of(2024, 6, 18, 21, 0, 0, 0, ZoneId.of("Europe/Berlin")),
                BetofficeData.REF_PORTUGAL, BetofficeData.REF_TSCHECHIEN);
    }

}
