package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exception.ResourceNotFoundException;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(User userCredentials){
//        User newUser = User.builder().firstName(userCredentials.firstName()).build();
        userRepository.save(userCredentials);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void replaceUser(Long id, User newUser){
        User oldUser = getUser(id);
        newUser.setId(oldUser.getId());
        userRepository.save(newUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
