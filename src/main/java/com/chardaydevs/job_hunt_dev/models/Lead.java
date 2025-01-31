package com.chardaydevs.job_hunt_dev.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "leads")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Must include job title")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Must include job title")
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "job_url")
    private String jobURL;

    @Column(name = "job_posting_date")
    private Date jobPostingDate;

    @Column(name = "location")
    private String location;

    @Column(name = "level")
    private String level;

    @NotBlank(message = "Status cannot be null or empty")
    @Column(name = "status")
    private String status;

    @Column(name = "user_id", nullable = false)
    private UUID userId;


    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobURL() {return jobURL;}

    public void setJobURL(String jobURL) {this.jobURL = jobURL;}

    public Date getJobPostingDate() {return jobPostingDate;}

    public void setJobPostingDate(Date jobPostingDate) {this.jobPostingDate = jobPostingDate;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public String getLevel() {return level;}

    public void setLevel(String level) {this.level = level;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
