package de.betoffice.wrapper.cli;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.betoffice.wrapper.api.BetofficeApi;

@Service
public class EM2024Setup {

    private final BetofficeApi betofficeApi;

    public EM2024Setup(BetofficeApi betofficeApi) {
        this.betofficeApi = betofficeApi;
    }

    @Transactional
    public void setup() {
        // betofficeApi.
    }

}
