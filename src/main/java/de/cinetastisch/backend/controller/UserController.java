package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            tags = {"Users"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(
            tags = {"Users"}
    )
    @PostMapping
    public ResponseEntity<?> addOne(@RequestBody User request) {
        userService.registerUser(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            tags = {"Users"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> replaceOne(@PathVariable("id") Long id, @RequestBody User newUser) {
        userService.replaceUser(id, newUser);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            tags = {"Users"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
