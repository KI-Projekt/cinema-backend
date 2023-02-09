package de.cinetastisch.backend.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Mock
    User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);


    @Test
    void testEquals() {
        user.setId((long)1.222);
        User user1 = user;
        User user2 = new User();
        user2.setId((long)1.2333);
        assertTrue(user.equals(user1));
        assertFalse(user.equals(user2));

    }

    @Test
    void testHashCode() {
        User user1 = user;
        assertEquals(user1.hashCode(), user.hashCode());
    }


    @Test
    void getFirstName() {
        assertEquals("Luca", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Chmiprogramierski", user.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("luca@gmail.com",user.getEmail());
    }

    @Test
    void getPassword() {
        assertEquals("12345", user.getPassword());
    }

    @Test
    void getBirthday() {
        assertEquals("12345", user.getPassword());

    }

    @Test
    void getCountry() {
        assertEquals("Deutschland", user.getCountry());

    }

    @Test
    void getCity() {
        assertEquals("Mannheim",user.getCity());
    }

    @Test
    void getZip() {
        assertEquals("68259", user.getZip());
    }

    @Test
    void getStreet() {
        assertEquals("Baumstr", user.getStreet());
    }

    @Test
    void getHouseNumber() {
        assertEquals(3, user.getHouseNumber());
    }

    @Test
    void setFirstName() {
        user.setFirstName("kevin");
        assertEquals("kevin", user.getFirstName());
    }

    @Test
    void setLastName() {
        user.setLastName("rieger");
        assertEquals("rieger", user.getLastName());
    }

    @Test
    void setEmail() {
        user.setEmail("kevin@gamil.com");
        assertEquals("kevin@gamil.com", user.getEmail());
    }

    @Test
    void setPassword() {
        user.setPassword("12");
        assertEquals("12", user.getPassword());
    }

    @Test
    void setBirthday() {
        user.setBirthday(LocalDate.of(2020,1,2));
        assertEquals(LocalDate.of(2020,1,2), user.getBirthday());
    }

    @Test
    void setCountry() {
        user.setCountry("Germany");
        assertEquals("Germany", user.getCountry());
    }

    @Test
    void setCity() {
        user.setCity("Stuttgart");
        assertEquals("Stuttgart",user.getCity());
    }

    @Test
    void setZip() {
        user.setZip("1234");
        assertEquals("1234", user.getZip());
    }

    @Test
    void setStreet() {
        user.setStreet("Test");
        assertEquals("Test",user.getStreet());
            }

    @Test
    void setHouseNumber() {
        user.setHouseNumber(5);
        assertEquals(5, user.getHouseNumber());
    }

    @Test
    void testToString() {
        String exp= "User(id=null, firstName=Luca, lastName=Chmiprogramierski, email=luca@gmail.com, password=12345, birthday=2020-01-02, country=Deutschland, city=Mannheim, zip=68259, street=Baumstr, houseNumber=3, role=ROLE_USER)";
        assertEquals(exp, user.toString());
    }
}