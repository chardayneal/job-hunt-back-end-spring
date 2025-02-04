package com.chardaydevs.job_hunt_dev.controllers;


import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public User authorizeUser(@RequestBody User user) {
        if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must provide email");
        }
        return this.userRepository.findByEmail(user.getEmail());
    }
}
