package de.betoffice.wrapper.cli;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.betoffice.wrapper.api.BetofficeApi;
import de.betoffice.wrapper.api.SeasonRef;
import de.betoffice.wrapper.data.BetofficeData;
import de.winkler.betoffice.storage.enums.SeasonType;
import de.winkler.betoffice.storage.enums.TeamType;

@Service
public class EM2024Setup {

    private final BetofficeApi api;

    public EM2024Setup(BetofficeApi betofficeApi) {
        this.api = betofficeApi;
    }

    @Transactional
    public void setupEM2024Vorrunde() {
        SeasonRef seasonRef = api.createSeason("EM Deutschland", "2024", SeasonType.EC, TeamType.FIFA).orThrow();
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
    }

}
