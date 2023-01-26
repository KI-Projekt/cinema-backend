package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;


    @Test
    void getAll() {
        User firstuser = new User("Luca", "Chmiprogramierski","luca@gmail.com","12345","2.2.22","Deutschland","Mannheim","68259","Baumstr", 3);
        User seconduser = new User("Luca", "Chmiprogramierski","luca@gmail.com","12345","2.2.22","Deutschland","Mannheim","68259","Baumstr", 3);
        List<User> userList = List.of(firstuser, seconduser);

        when(userService.getAllUsers()).thenReturn(userList);

        List<User> response = userController.getAll();
        assertEquals(userList,response);
    }

    @Test
    void getOne() {
        User firstuser = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", "2.2.22", "Deutschland", "Mannheim", "68259", "Baumstr", 3);

        when(userService.getUser((long) 1.2)).thenReturn(firstuser);

        User response = userController.getOne((long) 1.2);
        assertEquals(firstuser, response);
    }

//    Remaining requests are not testable, as they are only another method call of a service method
//    that is tested separately. In addition, there is no comparable return value.



}