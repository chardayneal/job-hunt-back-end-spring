package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.History;
import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import com.chardaydevs.job_hunt_dev.services.HistoryService;
import com.chardaydevs.job_hunt_dev.services.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadRepository leadRepository;
    private final LeadService leadService;
    private final HistoryService historyService;

    public LeadController(LeadRepository leadRepository, LeadService leadService, HistoryService historyService) {
        this.leadRepository = leadRepository;
        this.leadService = leadService;
        this.historyService = historyService;
    }

    @PostMapping("/{id}/history")
    public ResponseEntity<Lead> addHistoryToLead(@PathVariable("id") String id, @RequestBody @Valid History history) {
        Lead lead = this.leadService.validateLead(id);
        Lead updatedLead = this.historyService.addHistoryToLead(lead, history);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedLead);
    }

    @GetMapping("/{id}")
    public Lead getLeadById(@PathVariable("id") String id) {
        return this.leadService.validateLead(id);
    }

    @GetMapping("/{id}/history")
    public List<History> getAllHistoryByLeadId(@PathVariable("id") String id) {
        Lead lead = this.leadService.validateLead(id);
        return lead.getHistoryList();
    }

    @PatchMapping("/{id}")
    public Lead updateLeadById(@PathVariable("id") String id, @RequestBody Lead l) {
        this.leadService.validateLeadFields(l);

        return this.leadService.updateLead(id, l);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        Lead lead = this.leadService.validateLead(id);
        this.leadRepository.delete(lead);

        return "Lead successfully deleted!";
    }


}
