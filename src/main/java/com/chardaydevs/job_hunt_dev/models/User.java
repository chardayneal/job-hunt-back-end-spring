package com.chardaydevs.job_hunt_dev.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "User's name cannot be empty or null")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "User's email cannot be empty or null")
    @Email(message = "Invalid user email format")
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lead> leads = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Lead> getLeads() {
        return leads;
    }

    public void setLeads(List<Lead> leads) {
        this.leads = leads;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
