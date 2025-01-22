package com.chardaydevs.job_hunt_dev.controllers;

import java.util.Optional;

import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        try {
            Integer userId = Integer.parseInt(id);

            Optional<User> user = this.userRepository.findById(userId);
            if (!user.isPresent()) {
                String errorString = String.format("Error Fetching User: %s does not exist", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot retrieve User: does not exist");
            }
            return user;
        } catch (NumberFormatException error) {
            String errorString = String.format("Error Fetching User: %s is an invalid id", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorString);
        }
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        User newUser = this.userRepository.save(user);
        return newUser;
    }

}
