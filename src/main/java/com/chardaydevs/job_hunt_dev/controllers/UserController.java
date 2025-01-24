package com.chardaydevs.job_hunt_dev.controllers;

import java.util.Optional;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;

import com.chardaydevs.job_hunt_dev.services.UserLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;
    private final UserLeadService userLeadService;

    @Autowired
    public UserController(final UserRepository userRepository, UserLeadService userLeadService) {
        this.userRepository = userRepository;
        this.userLeadService = userLeadService;
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @PostMapping("/{id}/leads")
    public ResponseEntity<Lead> createLead(@PathVariable("id") Integer parentId, @RequestBody Lead lead) {
        Lead savedLead = userLeadService.addLeadToUser(parentId, lead);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLead);
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
