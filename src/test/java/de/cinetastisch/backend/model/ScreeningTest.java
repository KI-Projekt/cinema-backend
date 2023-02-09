package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.ScreeningStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {
    @Mock
    Screening screening = new Screening();


    @Test
    void testEquals() {
        screening.setId((long) 1.222);
        Screening screening1 = screening;
        Screening screening2 = new Screening();
        screening2.setId((long) 1.333);
        screening2.setStatus(ScreeningStatus.CANCELLED);
        assertTrue(screening.equals(screening1));
        assertFalse(screening.equals(screening2));


    }

    @Test
    void testHashCode() {
        Screening screening1 = screening;
        assertEquals(screening.hashCode(), screening1.hashCode());
    }

    @Test
    void testToString() {
        String exp = "Screening(id=null, movie=null, room=null, startDateTime=null, endDateTime=null, isThreeD=false, isDolbyAtmos=false, status=TICKET_SALE_OPEN)";
        assertEquals(exp, screening.toString());
    }
    @Test
    void testUpdateStatus(){
        screening.setStatus(ScreeningStatus.TICKET_SALE_OPEN);
        screening.setEndDateTime(LocalDateTime.now().minusMinutes(2));
        screening.updateStatus();
        assertEquals(ScreeningStatus.TICKET_SALE_CLOSED,screening.getStatus());
        screening.setStatus(ScreeningStatus.TICKET_SALE_OPEN);
        screening.setEndDateTime(LocalDateTime.now().plusMinutes(3));
        screening.updateStatus();
        assertEquals(ScreeningStatus.TICKET_SALE_OPEN,screening.getStatus());


    }
}