package de.cinetastisch.backend.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

class OpeningHourTest {
    LocalTime localTime = LocalTime.of(12,12,12);
    LocalTime localTimenew = LocalTime.of(12,12,12);

    OpeningHour openingHour = new OpeningHour(DayOfWeek.FRIDAY,localTime,localTime);

    @Test
    void getWeekday() {
        assertEquals(DayOfWeek.FRIDAY, openingHour.getWeekday());
    }

    @Test
    void getOpeningtime() {
        assertEquals(localTime, openingHour.getClosingtime());
    }

    @Test
    void getClosingtime() {
        assertEquals(localTime, openingHour.getOpeningtime());
    }

    @Test
    void setWeekday() {
        openingHour.setWeekday(DayOfWeek.MONDAY);
        assertEquals(DayOfWeek.MONDAY, openingHour.getWeekday());
    }

    @Test
    void setOpeningtime() {
        openingHour.setOpeningtime(localTimenew);
        assertEquals(localTimenew, openingHour.getOpeningtime());

    }

    @Test
    void setClosingtime() {
        openingHour.setClosingtime(localTimenew);
        assertEquals(localTimenew, openingHour.getClosingtime());
    }

    @Test
    void testEquals() {
        OpeningHour openingHour1 = openingHour;

        assertTrue(openingHour.equals(openingHour1));

        OpeningHour openingHour2 = new OpeningHour();
        OpeningHour openingHour3  = new OpeningHour();

        assertTrue(openingHour2.equals(openingHour3));
        assertFalse(openingHour2.equals(openingHour));

    }
    @Test
    void testToString() {
        String expectet = "OpeningHour(id=null, weekday=FRIDAY, openingtime=12:12:12, closingtime=12:12:12)";
        assertEquals(expectet,openingHour.toString());

    }
}