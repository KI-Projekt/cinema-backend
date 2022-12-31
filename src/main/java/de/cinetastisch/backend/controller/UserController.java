package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void addOne(@RequestBody User request) {
        userService.registerUser(request);
    }

    @PutMapping("/{id}")
    public void replaceOne(@PathVariable("id") Long id, @RequestBody User newUser) {
        userService.replaceUser(id, newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
}
