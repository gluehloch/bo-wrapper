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

        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_DEUTSCHLAND);
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_SCHOTTLAND);
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_SCHWEIZ);
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_A, BetofficeData.REF_UNGARN);

        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_ALBANIEN);
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_ITALIEN);
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_KROATIEN);
        api.addTeam(seasonRef, BetofficeData.REF_GRUPPE_B, BetofficeData.REF_SPANIEN);
    }

}
