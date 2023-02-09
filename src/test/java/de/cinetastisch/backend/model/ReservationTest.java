package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.MovieRating;
import de.cinetastisch.backend.enumeration.MovieStatus;
import de.cinetastisch.backend.enumeration.SeatCategory;
import org.mockito.Mock;

import java.time.LocalDate;

class ReservationTest {
    @Mock
    User user = new User("Peter", "Schmitt", "p.s@mail.de", "password", LocalDate.of(2022, 2, 2), "Deutschland", "Mannheim", "68245", "Strasse", 4);
    private final String session = "test";

    @Mock
    Movie movie = new Movie("Avengers Endgame", "2019", "/src/datei.png", MovieRating.PG13, "120", "Action", "Anthony Russo", "Christopher Markus", "Chris Evens", "Viel BumBum", "www.youtube.com/Endgame", "1234IMdb", "27/10", "1222", MovieStatus.IN_CATALOG);

    @Mock
    Room room = new Room("Room1", true, true);
    @Mock
    Screening screening = new Screening();

    @Mock
    Seat seat = new Seat(room, 2, 3, SeatCategory.NORMAL);
    @Mock
    Order order = new Order(user, session);

//    @Mock
//    Reservation reservation = new Reservation(user, screening, seat, order);
//
//
//    @Test
//    void testEquals() {
//        reservation.setId((long)1.22);
//        Reservation reservation1 = reservation;
//        Reservation reservation2 = new Reservation();
//        assertTrue(reservation.equals(reservation1));
//        assertFalse(reservation.equals(reservation2));
//    }
//
//    @Test
//    void testHashCode() {
//        Reservation act = reservation;
//        assertEquals(reservation.hashCode(), act.hashCode());
//    }
//
//
//    @Test
//    void getUser() {
//        assertEquals(reservation.getUser(), user);
//    }
//
//    @Test
//    void getScreening() {
//        assertEquals(reservation.getScreening(), screening);
//    }
//
//    @Test
//    void getSeat() {
//        assertEquals(reservation.getSeat(), seat);
//    }
//
//    @Test
//    void getOrder() {
//        assertEquals(reservation.getOrder(), order);
//    }
//
//    @Test
//    void setUser() {
//        User user1 = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);
//        reservation.setUser(user1);
//        assertEquals(user1, reservation.getUser());
//    }
//
//    @Test
//    void setScreening() {
//        Screening screening1 = new Screening();
//        reservation.setScreening(screening1);
//        assertEquals(screening1, reservation.getScreening());
//    }
//
//    @Test
//    void setSeat() {
//        Seat seat1 = new Seat(room, 2, 3, SeatCategory.NORMAL);
//        reservation.setSeat(seat1);
//        assertEquals(seat1, reservation.getSeat());
//    }
//
//    @Test
//    void setOrder() {
//        Order order1 = new Order(user);
//        reservation.setOrder(order1);
//        assertEquals(order1, reservation.getOrder());
//    }


}