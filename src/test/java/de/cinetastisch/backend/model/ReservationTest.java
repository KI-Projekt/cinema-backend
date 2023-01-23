package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.SeatCategory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    @Mock
    User user = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);

    @Mock
    Movie movie = new Movie("Avengers Endgame","2019","/src/datei.png", MovieRating.PG13,"120","Action","Anthony Russo","Christopher Markus","Chris Evens","Viel BumBum","www.youtube.com/Endgame","1234IMdb","27/10","1222");

    @Mock
    Room room = new Room("Room1", true, true );
    @Mock
    Screening screening = new Screening();

    @Mock
    Seat seat = new Seat(room, 2, 3, SeatCategory.NORMAL);
    @Mock
    Order order = new Order(user);

    @Mock
    Reservation reservation = new Reservation(user, screening, seat, order);


    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
//        int act = reservation.hashCode();
//        int exp = 185783387;
//        assertEquals(exp, act);
    }


    @Test
    void getUser() {
        reservation.setId((long)1.22);
        User exp = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);
        User act = reservation.getUser();
        assertEquals(exp, act);
    }

    @Test
    void getScreening() {
    }

    @Test
    void getSeat() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void getCreatedAt() {
    }

    @Test
    void getExpiresAt() {
    }

    @Test
    void setUser() {
    }

    @Test
    void setScreening() {
    }

    @Test
    void setSeat() {
    }

    @Test
    void setOrder() {
    }

    @Test
    void setExpiresAt() {
    }

    @Test
    void testToString() {
    }
}