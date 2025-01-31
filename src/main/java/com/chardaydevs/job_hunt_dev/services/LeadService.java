package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    @Autowired
    public LeadService(final LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Transactional
    public Lead validateLead(String id) {
        try {
            UUID leadId = UUID.fromString(id);

            Optional<Lead> foundLead = this.leadRepository.findById(leadId);
            if (foundLead.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error Fetching Lead: Lead does not exist");
            }

            return foundLead.get();
        } catch (NumberFormatException error) {
            String errorString = String.format("Error Fetching Lead: %s is an invalid id", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorString);
        }
    }

    @Transactional
    public void validateLeadFields( Lead l) {

        if (l == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (l.getTitle() == null & l.getCompany() == null & l.getJobURL() == null & l.getDescription() == null & l.getJobURL() == null & l.getStatus() == null & l.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (l.getTitle() != null && l.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        if (l.getCompany() != null && l.getCompany().isEmpty()) {
            throw new IllegalArgumentException("Company cannot be null or empty");
        }

        if (l.getStatus() != null && l.getStatus().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }

    @Transactional
    public Lead updateLead(String id, Lead l) {
        Lead lead = validateLead(id);

        if (l.getTitle() != null) {
            lead.setTitle(l.getTitle());
        }
        if (l.getCompany() != null) {
            lead.setCompany(l.getCompany());
        }
        if (l.getJobURL() != null) {
            lead.setJobURL(l.getJobURL());
        }
        if (l.getDescription() != null) {
            lead.setDescription(l.getDescription());
        }
        if (l.getStatus() != null) {
            lead.setStatus(l.getStatus());
        }
        if (l.getLocation() != null) {
            lead.setLocation(l.getLocation());
        }
        if (l.getLevel() != null) {
            lead.setLocation(l.getLevel());
        }
        return this.leadRepository.save(lead);
    }
}
