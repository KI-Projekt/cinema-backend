package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.dto.response.UserResponseDto;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.exception.ResourceHasChildrenException;
import de.cinetastisch.backend.mapper.UserMapper;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoder encoder;

    @Test
    void registerUser() {
        UserResponseDto userResponseDto = new UserResponseDto(null, null, null, null, null);
        UserRequestDto userRequestDto = new UserRequestDto("Peter", "Mustermann", "muster@mail.de", "1234", null, null, null, null, null, null, null);
        User user = new User(null, null, null, null, null, null, null, null, null, null);
        when(userRepository.existsByEmail(userRequestDto.email())).thenReturn(false);
        when(userMapper.dtoToEntity(userRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(userResponseDto);
        UserResponseDto response = userService.registerUser(userRequestDto);
        assertEquals(userResponseDto, response);
    }

    @Test
    void registerUserException(){
        UserRequestDto userRequestDto = new UserRequestDto("Peter", "Mustermann", "muster@mail.de", "1234", null, null, null, null, null, null, null);
        when(userRepository.existsByEmail(userRequestDto.email())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, ()->userService.registerUser(userRequestDto));
    }

    @Test
    void getUser() {
        User user = new User(null, null, null, null, null, null, null, null, null, null);
        UserResponseDto userResponseDto = new UserResponseDto(null, null, null, null, null);
        when(userRepository.getReferenceById((long)1.2)).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(userResponseDto);
        UserResponseDto response = userService.getUser((long)1.2);
        assertEquals(userResponseDto, response);
    }

    @Test
    void getAllUsers() {
        User user = new User(null, null, null, null, null, null, null, null, null, null);
        User user2 = new User(null, null, null, null, null, null, null, null, null, null);
        List<User> userList = List.of(user, user2);
        UserResponseDto userResponseDto = new UserResponseDto(null, null, null, null, null);
        UserResponseDto userResponseDto2 = new UserResponseDto(null, null, null, null, null);
        List<UserResponseDto> responseDtoList = List.of(userResponseDto, userResponseDto2);
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.entityToDto(userList)).thenReturn(responseDtoList);

        List<UserResponseDto> response = userService.getAllUsers();
        assertEquals(responseDtoList,response);
    }

    @Test
    void replaceUser() {
        UserResponseDto userResponseDto = new UserResponseDto(null, null, null, null, null);
        User user = new User(null, null, null, null, null, null, null, null, null, null);
        when(userRepository.getReferenceById((long)1.2)).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(userResponseDto);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDto response = userService.replaceUser((long)1.2, user);
        assertEquals(userResponseDto, response);
    }

    @Test
    void deleteUser() {

    }
}