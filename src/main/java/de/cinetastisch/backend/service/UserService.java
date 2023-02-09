package de.cinetastisch.backend.service;

import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.dto.response.UserResponseDto;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.mapper.UserMapper;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder encoder;

    public UserResponseDto registerUser(UserRequestDto userCredentials){
        if(userRepository.existsByEmail(userCredentials.email())){
            throw new ResourceAlreadyExistsException("User already exists with this email");
        }
        User user = userMapper.dtoToEntity(userCredentials);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.entityToDto(user);
}

    public UserResponseDto getUser(Long id) {
        return userMapper.entityToDto(userRepository.getReferenceById(id));
    }

    public List<UserResponseDto> getAllUsers() {
        return userMapper.entityToDto(userRepository.findAll());
    }

    public UserResponseDto replaceUser(Long id, User newUser){
        newUser.setId(userRepository.getReferenceById(id).getId());
        return userMapper.entityToDto(userRepository.save(newUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
