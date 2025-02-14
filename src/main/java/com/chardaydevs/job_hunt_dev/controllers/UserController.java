package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.Insight;
import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.Task;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;

import com.chardaydevs.job_hunt_dev.services.InsightService;
import com.chardaydevs.job_hunt_dev.services.UserLeadService;
import com.chardaydevs.job_hunt_dev.services.UserService;
import com.chardaydevs.job_hunt_dev.services.UserTaskService;
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
    private final UserTaskService userTaskService;
    private final UserService userService;
    private final InsightService insightService;

    @Autowired
    public UserController(final UserRepository userRepository, UserLeadService userLeadService, UserTaskService userTaskService,  UserService userService, InsightService insightService) {
        this.userRepository = userRepository;
        this.userLeadService = userLeadService;
        this.userTaskService = userTaskService;
        this.userService = userService;
        this.insightService = insightService;

    }

    @PostMapping("/{id}/leads")
    public ResponseEntity<User> createLead(@PathVariable("id") String parentId, @RequestBody Lead lead) {
        User user = this.userService.validateUser(parentId);
        User updatedUser = this.userLeadService.addLeadToUser(user, lead);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<User> createTask(@PathVariable("id") String parentId, @Valid @RequestBody Task task) {
        User user = this.userService.validateUser(parentId);
        User updatedUser = this.userTaskService.addTaskToUser(user, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @PostMapping("/{id}/insights")
    public ResponseEntity<User> createInsight(@PathVariable("id") String parentId, @Valid @RequestBody Insight insight) {
        User user = this.userService.validateUser(parentId);
        User updatedUser = this.insightService.addInsightToUser(user, insight);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
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

    @GetMapping("/{id}/tasks")
    public Iterable<Task> getAllTasksByUserId(@Valid @PathVariable("id") String id) {
        User user = this.userService.validateUser(id);
        return user.getTasks();
    }

    @GetMapping("/{id}/insights")
    public Iterable<Insight> getAllInsightsByUserId(@Valid @PathVariable("id") String id) {
        User user = this.userService.validateUser(id);
        return user.getInsights();
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User u) {
        this.userService.validateUserFields(u);
        return this.userService.updateUser(id, u);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        User foundUser = this.userService.validateUser(id);
        this.userRepository.delete(foundUser);

        return "User successfully deleted";
    }
}
