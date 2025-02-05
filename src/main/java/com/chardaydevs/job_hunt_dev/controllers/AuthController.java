package com.chardaydevs.job_hunt_dev.controllers;


import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import com.chardaydevs.job_hunt_dev.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AuthController(final UserRepository userRepository, final UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User createUser(@Valid @RequestBody User user) {
        if (this.userRepository.findByEmail(user.getEmail()).isEmpty()) {
            return this.userService.createValidUser(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email already in use");
    }

    @PostMapping("/signin")
    public User authorizeUser(@RequestBody User user) {
        if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must provide email");
        }
        return this.userService.signInUser(user);
    }

    @GetMapping("/token")
    public User validateUserByToken(@RequestParam String id) {
        return this.userService.getUserByToken(id);
    }
}
