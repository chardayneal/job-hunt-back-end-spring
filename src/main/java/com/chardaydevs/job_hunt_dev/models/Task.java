package com.chardaydevs.job_hunt_dev.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Task text cannot be null or empty")
    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "is_complete")
    private Boolean isComplete = false;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        this.isComplete = complete;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
