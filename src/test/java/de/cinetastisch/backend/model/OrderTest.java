package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Mock
    User user = new User("Peter", "Schmitt", "p.s@mail.de", "password", "31.12.2000", "Deutschland", "Mannheim", "68245", "Strasse", 4);

    @Mock
    Order order = new Order(user);

    @Test
    void testEquals() {
        order.setId((long) 1.2222);
        Order testorder1 = order;
        Order testorder = new Order();
        Boolean act = order.equals(testorder1);
        assertTrue(act);
        assertFalse(order.equals(testorder));
    }

    @Test
    void testHashCode() {
        Order order1 = order;
        assertEquals(order.hashCode(), order1.hashCode());
    }


    @Test
    void getUser() {
        assertEquals(user,order.getUser());
    }

    @Test
    void getOrderStatus() {
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderStatus exp = OrderStatus.IN_PROGRESS;
        OrderStatus act = order.getStatus();
        assertEquals(exp, act);
    }

//    @Test
//    void getTotal() {
//        Double exp = 100.0;
//        order.setTotal(100.0);
//        Double act = order.getTotal();
//        assertEquals(exp,act);
//    }

    @Test
    void setUser() {
        User user1 = new User();
        order.setUser(user1);
        assertEquals(order.getUser(),user1);
    }

    @Test
    void setOrderStatus() {
        OrderStatus exp = OrderStatus.IN_PROGRESS;
        order.setStatus(OrderStatus.IN_PROGRESS);
        OrderStatus act = order.getStatus();
        assertEquals(exp, act);
    }

//    @Test
//    void setTotal() {
//        Double exp = 100.0;
//        order.setTotal(100.0);
//        Double act = order.getTotal();
//        assertEquals(exp,act);
//    }

//    @Test
//    void testToString() {
//        String act = "Order(id=null, user=User(id=null, firstName=Peter, lastName=Schmitt, email=p.s@mail.de, password=password, birthday=31.12.2000, country=Deutschland, city=Mannheim, zip=68245, street=Strasse, houseNumber=4), orderStatus=IN_PROGRESS, total=null)";
//        //order.setId((long)1.222);
//        String exp = order.toString();
//        assertEquals(exp, act);
//    }
}