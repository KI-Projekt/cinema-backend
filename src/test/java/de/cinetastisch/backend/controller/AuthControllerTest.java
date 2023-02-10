package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.UserLoginRequestDto;
import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.mapper.UserMapper;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.x500.X500Principal;
import java.security.Principal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTestt {
    @InjectMocks
    AuthController authController;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;



    @Test
    void registerUser() {
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        UserRequestDto requestDto = new UserRequestDto(null,null,"luca@gmail.com",null,null,null,null,null,null,null,null);

        when(userRepository.existsByEmail("luca@gmail.com")).thenReturn(false);
        when(userMapper.dtoToEntity(requestDto)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("1234");
        when(userRepository.save(user)).thenReturn(user);

        User user1 = authController.registerUser(requestDto);

        assertEquals("Luca", user1.getFirstName());

    }
    @Test
    void registerUserExeption() {
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        UserRequestDto requestDto = new UserRequestDto(null,null,"luca@gmail.com",null,null,null,null,null,null,null,null);

        when(userRepository.existsByEmail("luca@gmail.com")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class,()->authController.registerUser(requestDto));

    }


    @Test
    void check() {
        Principal principal =new X500Principal("");
        Principal response = authController.check(principal);
        assertEquals(principal,response);

    }
    @Test
    void checkNULL() {
        Principal principal = null;
        Principal respone = authController.check(principal);
        assertNull(respone);

    }

    @Test
    void checkUserExeption() {
        Principal principal = null;
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);
        assertThrows(AuthenticationCredentialsNotFoundException.class,()->authController.checkUser(principal));

    }
}