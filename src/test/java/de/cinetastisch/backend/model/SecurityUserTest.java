package de.cinetastisch.backend.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;

import javax.management.relation.RoleInfo;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserTest {
    @Mock
    User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);

@Mock
    SecurityUser securityUser = new SecurityUser(user);

    @Test
    void getUsername() {
         assertEquals("luca@gmail.com", securityUser.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("12345",securityUser.getPassword());
    }


    @Test
    void isAccountNonExpired() {
        assertTrue(securityUser.isAccountNonExpired());

    }
    @Test
    void getAuthorities(){
        Collection<? extends GrantedAuthority> test = securityUser.getAuthorities();
        assertEquals(securityUser.getAuthorities(),test);
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(securityUser.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(securityUser.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(securityUser.isEnabled());
    }
}