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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final OrderService orderService;

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
    @ResponseStatus(HttpStatus.OK)
    public User authenticateUser(@RequestBody UserLoginRequestDto loginDto, HttpServletRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getUserPrincipal().getName()).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (user.isFirstLogin()){
            user.setFirstLogin(false);
            userRepository.save(user);
            User response = new User(user.getFirstName(), user.getLastName(), user.getEmail(),user.getPassword(), user.getBirthday(), user.getCountry(), user.getCity(), user.getZip(), user.getStreet(), user.getHouseNumber());
            response.setRole(user.getRole());
            response.setFirstLogin(true);
            response.setId(user.getId());
            return response;
        }
        return user;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody UserRequestDto registerDto){
        if(userRepository.existsByEmail(registerDto.email())){
            throw new ResourceAlreadyExistsException("Email is already taken!");
        }

        User user = userMapper.dtoToEntity(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        return user;
    }

    @GetMapping
    public Principal check(Principal test){
        if(test != null){
            return test;
        }
        return null;
    }

    @GetMapping("/currentUser")
    public User checkUser(Principal test){
        if(test != null){
            return userRepository.getByEmail(test.getName());
        }
        throw new AuthenticationCredentialsNotFoundException("Not authenticated");
    }
}
