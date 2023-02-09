package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.dto.response.UserResponseDto;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;



    @Test
    void getAll() {
        UserResponseDto responseDto = new UserResponseDto(null,null,null,null,null);
        List<UserResponseDto> responseDtoList = List.of(responseDto,responseDto);

        when(userService.getAllUsers()).thenReturn(responseDtoList);
        ResponseEntity<?> response = userController.getAll();
        assertEquals(responseDtoList,response.getBody());
}
    @Test
    void getAllpricipal(){

    };
    @Test
    void getOne(){
        UserResponseDto responseDto = new UserResponseDto(null,null,null,null,null);
        when(userService.getUser((long)1.2)).thenReturn(responseDto);

        ResponseEntity<?> respone = userController.getOne((long)1.2);

        assertEquals(responseDto,respone.getBody());
        assertEquals(HttpStatus.OK,respone.getStatusCode());


    };
    @Test
    void registerUser(){
        UserResponseDto responseDto = new UserResponseDto(null,null,null,null,null);
        UserRequestDto requestDto =new UserRequestDto(null,null
        ,null,null,null,null,null,null,null,null,null);
        when(userService.registerUser(requestDto)).thenReturn(responseDto);

        ResponseEntity<?> response = userController.registerUser(requestDto);

        assertEquals(responseDto,response.getBody());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());



    };
    @Test
    void replaceOne(){
        UserResponseDto responseDto = new UserResponseDto(null,null,null,null,null);
        User user = new User("Luca", "Chmiprogramierski", "luca@gmail.com", "12345", LocalDate.of(2020, 1, 2), "Deutschland", "Mannheim", "68259", "Baumstr", 3);

        when(userService.replaceUser((long)1.2,user)).thenReturn(responseDto);

        ResponseEntity<?> response = userController.replaceOne((long)1.2,user);

        assertEquals(responseDto,response.getBody());


    };
    @Test
    void deleteOne(){
        ResponseEntity<?> response = userController.deleteOne((long)1.2);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

    };



}