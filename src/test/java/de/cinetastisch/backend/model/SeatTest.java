package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.SeatCategory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class SeatTest {
    @Mock
    Room room = new Room("Avengers", true, false);
    @Mock
    Seat seat1 = new Seat(room, 3, 5, SeatCategory.PREMIUM);


    @Mock
    Seat seat = new Seat(room, 4, 7, SeatCategory.NORMAL);

    @Test
    void testEquals() {
        seat.setId((long) 1.222);
        seat1.setId((long) 1.2222);
        assertEquals(seat, seat);
        assertNotEquals(seat1, seat);

    }

    @Test
    void testHashCode() {
        Seat seat2 = seat;
        assertEquals(seat.hashCode(), seat2.hashCode());
    }


    @Test
    void getRoom() {
        assertEquals(seat.getRoom(), room);
    }

    @Test
    void getRow() {
        assertEquals(4, seat.getRow());
    }

    @Test
    void getColumn() {
        assertEquals(4, seat.getRow());
    }

    @Test
    void getCategory() {
        assertEquals(SeatCategory.NORMAL, seat.getCategory());
    }


    @Test
    void setRoom() {
        Room room1 = new Room("Star Wars", false, true);
        seat.setRoom(room1);
        assertEquals(room1, seat.getRoom());
    }

    @Test
    void setRow() {
        seat.setRow(3);
        assertEquals(3, seat.getRow());
    }

    @Test
    void setColumn() {
        seat.setColumn(5);
        assertEquals(5, seat.getColumn());
    }

    @Test
    void setCategory() {
        seat.setCategory(SeatCategory.PREMIUM);
        assertEquals(SeatCategory.PREMIUM, seat.getCategory());
    }

    @Test
    void testToString() {
        String exp = "Seat(id=null, room=Room(id=null, name=Avengers, hasThreeD=true, hasDolbyAtmos=false), row=4, column=7, category=NORMAL)";
        assertEquals(exp, seat.toString());
    }
}