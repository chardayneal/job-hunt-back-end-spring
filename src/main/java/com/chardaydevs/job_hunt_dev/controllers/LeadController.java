package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import com.chardaydevs.job_hunt_dev.services.LeadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadRepository leadRepository;
    private final LeadService leadService;

    public LeadController(LeadRepository leadRepository, LeadService leadService) {
        this.leadRepository = leadRepository;
        this.leadService = leadService;
    }

    @GetMapping("/{id}")
    public Lead getLeadById(@PathVariable("id") String id) {
        return this.leadService.validateLead(id);
    }

    @PatchMapping("/{id}")
    public Lead updateLeadById(@PathVariable("id") String id, @RequestBody Lead l) {
        this.leadService.validateLeadFields(id, l);

        return this.leadService.updateLead(id, l);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        Lead lead = this.leadService.validateLead(id);
        this.leadRepository.delete(lead);

        return "Lead successfully deleted!";
    }


}
