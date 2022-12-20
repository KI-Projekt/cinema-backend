package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.model.pojo.UserPojo;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserPojo userCredentials){
        User newUser = new User();
        newUser.setFirstName(userCredentials.firstName);
        newUser.setLastName(userCredentials.lastName);
        newUser.setEmail(userCredentials.email);
        newUser.setPassword(userCredentials.password);
        userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
