package de.betoffice.wrapper.api;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;

class ZonedDateTimeTest {

    @Test
    void zonedDateTime() {
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        assertThat(zoneId).isNotNull();
    }


}
