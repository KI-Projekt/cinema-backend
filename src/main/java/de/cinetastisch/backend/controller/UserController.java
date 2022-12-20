package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.model.pojo.UserPojo;
import de.cinetastisch.backend.record.UserRequestBody;
import de.cinetastisch.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getAllUsers(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public void add(@RequestBody UserPojo request){ // statt "User" kann auch ein eigener record benutzt werden
        userService.registerUser(request);
    }

}
