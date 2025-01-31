package com.chardaydevs.job_hunt_dev.controllers;

import java.util.UUID;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;

import com.chardaydevs.job_hunt_dev.services.UserLeadService;
import com.chardaydevs.job_hunt_dev.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;
    private final UserLeadService userLeadService;
    private final UserService userService;

    @Autowired
    public UserController(final UserRepository userRepository, UserLeadService userLeadService, UserService userService) {
        this.userRepository = userRepository;
        this.userLeadService = userLeadService;
        this.userService = userService;
    }


    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {
         return this.userService.createValidUser(user);
    }

    @PostMapping("/{id}/leads")
    public ResponseEntity<Lead> createLead(@PathVariable("id") String parentId, @Valid @RequestBody Lead lead) {
        User user = this.userService.validateUser(parentId);
        Lead savedLead = userLeadService.addLeadToUser(user, lead);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLead);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return this.userService.validateUser(id);
    }

    @GetMapping("/{id}/leads")
    public Iterable<Lead> getAllLeadsByUserId(@Valid @PathVariable("id") String id) {
        User user = this.userService.validateUser(id);
        return user.getLeads();
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User u) {
        this.userService.validateUserFields(id, u);

        return this.userService.updateUser(id, u);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        User foundUser = this.userService.validateUser(id);
        this.userRepository.delete(foundUser);

        return "User successfully deleted";
    }
}
