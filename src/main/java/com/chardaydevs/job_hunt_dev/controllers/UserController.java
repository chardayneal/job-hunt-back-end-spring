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

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        if (user.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Creating User: must include name");
        } else if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Creating User: must include email");
        }

        return this.userRepository.save(user);
    }


    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return validateUser(id);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User u) {
        User foundUser = validateUser(id);

//        validate request Body
        if (u.getName() != null) {
            foundUser.setName(u.getName());
        }
        if (u.getEmail() != null) {
            foundUser.setEmail(u.getEmail());
        }
        return this.userRepository.save(foundUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        User foundUser = validateUser(id);
        this.userRepository.delete(foundUser);

        return "User successfully deleted";
    }


    public User validateUser(String id) {
        try {
            Integer userId = Integer.parseInt(id);

            Optional<User> foundUser = this.userRepository.findById(userId);
            if (foundUser.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error Fetching User: User does not exist");
            }

            return foundUser.get();
        } catch (NumberFormatException error) {
            String errorString = String.format("Error Fetching User: %s is an invalid id", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorString);
        }
    }
}
