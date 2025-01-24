package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadRepository leadRepository;

    public LeadController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @GetMapping("")
    public Iterable<Lead> getAllLeads() {
        return this.leadRepository.findAll();
    }

    @GetMapping("/{id}")
    public Lead getLeadById(@PathVariable("id") String id) {
        return validateLead(id);
    }

    @PutMapping("/{id}")
    public Lead updateLeadById(@PathVariable("id") String id, @RequestBody Lead l) {
        Lead lead = validateLead(id);

        if (l.getTitle() != null) {
            lead.setTitle(l.getTitle());
        }
        if (l.getCompany() != null) {
            lead.setCompany(l.getCompany());
        }
        return this.leadRepository.save(lead);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        Lead lead = validateLead(id);
        this.leadRepository.delete(lead);

        return "Lead successfully deleted!";
    }


    public Lead validateLead(String id) {
        try {
            Integer leadId = Integer.parseInt(id);

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
}
