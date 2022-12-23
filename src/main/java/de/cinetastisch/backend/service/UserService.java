package de.cinetastisch.backend.service;

import de.cinetastisch.backend.exeption.ResourceNotFoundException;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.MovieRepository;
import de.cinetastisch.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(User userCredentials){
        User newUser = new User();
        newUser.setFirstName(userCredentials.getFirstName());
        newUser.setLastName(userCredentials.getLastName());
        newUser.setEmail(userCredentials.getEmail());
        newUser.setPassword(userCredentials.getPassword());
        userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void replaceUser(Long id, User newUser){
        User oldUser = userRepository.findById(id).get();
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setAddress(newUser.getAddress());
        oldUser.setBirthday(newUser.getBirthday());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setEmail(newUser.getEmail());
        userRepository.save(oldUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
