package de.cinetastisch.backend.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    @Mock
    Room room = new Room( "Avengers", true, false);

    @Test
    void testEquals() {
//        Room testroom = new Room();
//        Room testequalsroom = room;
//        Boolean act = room.equals(testequalsroom);
//        assertTrue(act);
//        assertFalse(room.equals(testroom));
    }

    @Test
    void testHashCode() {
        int act = room.hashCode();
        int exp = -896815484;
        assertEquals(exp, act);
    }

    @Test
    void getName() {
        String exp = "Avengers";
        String act = room.getName();
        assertEquals(exp, act);
    }

    @Test
    void getHasThreeD() {
        Boolean act = room.getHasThreeD();
        assertTrue( act);
    }

    @Test
    void getHasDolbyAtmos() {
        Boolean act = room.getHasDolbyAtmos();
        assertFalse(act);
    }


    @Test
    void setName() {
        String exp = "Marvel";
        room.setName("Marvel");
        String act = room.getName();
        assertEquals(exp, act);
    }

    @Test
    void setHasThreeD() {
        room.setHasThreeD(true);
        Boolean act = room.getHasThreeD();
        assertTrue(act);
    }

    @Test
    void setHasDolbyAtmos() {
        room.setHasDolbyAtmos(true);
        Boolean act = room.getHasDolbyAtmos();
        assertTrue(act);
    }

    @Test
    void testToString() {
        String act = room.toString();
        String exp = "Room(id=null, name=Avengers, hasThreeD=true, hasDolbyAtmos=false)";
        assertEquals(exp, act);
    }
}