package de.cinetastisch.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketFareTest {

    TicketFare ticketFare = new TicketFare((long)1.2,null,2.0,null);
    TicketFare ticketFare1 = new TicketFare(null,2.0,null);

    @Test
    void getId() {
        assertEquals((long)1.2, ticketFare.getId());
    }

    @Test
    void getName() {
        assertNull(ticketFare1.getName());
    }

    @Test
    void getPrice() {
        assertEquals(2.0,ticketFare1.getPrice());
    }

    @Test
    void getFareCondition() {
        assertNull(ticketFare.getFareCondition());
    }

    @Test
    void setId() {
        ticketFare1.setId((long)1.2);
        assertEquals((long)1.2, ticketFare1.getId());
    }

    @Test
    void setName() {
        ticketFare1.setName("Test");
        assertEquals("Test", ticketFare1.getName());
    }

    @Test
    void setPrice() {
        ticketFare1.setPrice(3.0);
        assertEquals(3.0, ticketFare1.getPrice());
    }

    @Test
    void setFareCondition() {
        ticketFare1.setFareCondition("test");
        assertEquals("test", ticketFare1.getFareCondition());
    }

    @Test
    void testEquals() {
        TicketFare ticketFare2 = new TicketFare();
        TicketFare ticketFare3 = ticketFare2;

        assertTrue(ticketFare2.equals(ticketFare3));
        assertFalse(ticketFare2.equals(ticketFare));
    }

    @Test
    void testToString() {
        String exp = "TicketFare{id=null, name='null', price=2.0, condition='null'}";
        assertEquals(exp, ticketFare1.toString());
    }
}