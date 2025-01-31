package com.chardaydevs.job_hunt_dev.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "history")
@JsonIgnoreProperties(ignoreUnknown = true)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Status cannot be null or empty")
    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "lead_id", nullable = false)
    private UUID leadId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }
}
