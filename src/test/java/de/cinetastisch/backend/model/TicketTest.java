package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.SeatCategory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    @Mock
    User user = new User("Luca", "Chmiprogramierski","luca@gmail.com","12345","2.2.22","Deutschland","Mannheim","68259","Baumstr", 3);

    @Mock
    Screening screening = new Screening();

    @Mock
    Room room = new Room("Avengers", true, false);

    @Mock
    Seat seat = new Seat(room, 4, 7, SeatCategory.NORMAL);

    @Mock
    Order order = new Order(user);
    @Mock
    Ticket ticket = new Ticket(order,screening,seat, null);

    @Test
    void testEquals() {
        ticket.setId((long)1.2222);
        Ticket ticket1 = ticket;
        Ticket ticket2 = new Ticket();
        ticket2.setId((long)1.444);
        assertTrue(ticket.equals(ticket1));
        assertFalse(ticket.equals(ticket2));
    }

    @Test
    void testHashCode() {
        Ticket ticket1 = ticket;
        assertEquals(ticket.hashCode(), ticket1.hashCode());

    }


    @Test
    void getOrder() {
        assertEquals(order, ticket.getOrder());
    }

    @Test
    void getScreening() {
        assertEquals(screening, ticket.getScreening());
    }

    @Test
    void getSeat() {
        assertEquals(seat, ticket.getSeat());
            }

    @Test
    void getSelectedFare() {
        TicketFare fare1 = new TicketFare();
        ticket.setSelectedFare(fare1);
        assertEquals(fare1, ticket.getSelectedFare());
    }

    @Test
    void setOrder() {
        Order order1 = new Order();
        ticket.setOrder(order1);
        assertEquals(order1, ticket.getOrder());
    }

    @Test
    void setScreening() {
        Screening screening1 = new Screening();
        ticket.setScreening(screening1);
        assertEquals(screening1, ticket.getScreening());
    }

    @Test
    void setSeat() {
        Seat seat1 = new Seat();
        ticket.setSeat(seat1);
        assertEquals(seat1, ticket.getSeat());
    }

    @Test
    void setSelectedFare() {
        TicketFare fare1 = new TicketFare();
        ticket.setSelectedFare(fare1);
        assertEquals(fare1, ticket.getSelectedFare());
    }

//    @Test
//    void testToString() {
//        String exp = "Ticket(id=null, order=Order(id=null, user=User(id=null, firstName=Luca, lastName=Chmiprogramierski, email=luca@gmail.com, password=12345, birthday=2.2.22, country=Deutschland, city=Mannheim, zip=68259, street=Baumstr, houseNumber=3), orderStatus=IN_PROGRESS, total=null), screening=Screening(id=null, movie=null, room=null, startDateTime=null, endDateTime=null, status=TICKET_SALE_OPEN), seat=Seat(id=null, room=Room(id=null, name=Avengers, hasThreeD=true, hasDolbyAtmos=false), row=4, column=7, category=NORMAL), category=STUDENT)";
//        assertEquals(exp , ticket.toString());
//    }
}