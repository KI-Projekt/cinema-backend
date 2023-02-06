package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.UserLoginRequestDto;
import de.cinetastisch.backend.dto.request.UserRequestDto;
import de.cinetastisch.backend.exception.ResourceAlreadyExistsException;
import de.cinetastisch.backend.mapper.UserMapper;
import de.cinetastisch.backend.model.User;
import de.cinetastisch.backend.repository.UserRepository;
import de.cinetastisch.backend.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    private OrderService orderService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, UserMapper userMapper, OrderService orderService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.orderService = orderService;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginRequestDto loginDto, HttpServletRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        orderService.transferOrderToUser(request);

        return new ResponseEntity<>("User signed in successfully! " + authentication.toString(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto registerDto){
        if(userRepository.existsByEmail(registerDto.email())){
            throw new ResourceAlreadyExistsException("Email is already taken!");
        }

        User user = userMapper.dtoToEntity(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER,ROLE_ADMIN");

        userRepository.save(user);
        return new ResponseEntity<>("User registered sicessfully!", HttpStatus.OK);
    }

    @GetMapping
    public Principal check(Principal test){
        if(test != null){
            return test;
        }
        return null;
    }
}
