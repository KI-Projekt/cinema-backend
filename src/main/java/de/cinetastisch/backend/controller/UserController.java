package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            tags = {"Users"}
    )
    @GetMapping()
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @Operation(
            tags = {"Users"}
    )
    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @Operation(
            tags = {"Users"}
    )
    @PostMapping
    public void addOne(@RequestBody User request) {
        userService.registerUser(request);
    }

    @Operation(
            tags = {"Users"}
    )
    @PutMapping("/{id}")
    public void replaceOne(@PathVariable("id") Long id, @RequestBody User newUser) {
        userService.replaceUser(id, newUser);
    }

    @Operation(
            tags = {"Users"}
    )
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }
}
