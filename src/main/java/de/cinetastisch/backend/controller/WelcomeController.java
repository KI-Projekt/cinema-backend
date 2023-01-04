package de.cinetastisch.backend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class WelcomeController {

    @GetMapping("welcome")
    public String wish(){
        return "Welcome Schnubbi";
    }
}
