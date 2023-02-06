package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.dto.response.UserResponseDto;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            tags = {"Users"}
    )
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            tags = {"Users"}
    )
    @GetMapping("/session")
    public ResponseEntity<List<UserResponseDto>> getAll(Principal principal) {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @Operation(
            tags = {"Users"}
    )
//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(
            tags = {"Users"}
    )
    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto request) {
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }



    @Operation(
            tags = {"Users"}
    )
//    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> replaceOne(@PathVariable("id") Long id, @RequestBody User newUser) {
        return new ResponseEntity<>(userService.replaceUser(id, newUser), HttpStatus.OK);
    }

    @Operation(
            tags = {"Users"}
    )
//    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
