package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.models.History;
import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserLeadService {

    private final UserRepository userRepository;
    private final LeadService leadService;
    private final HistoryService historyService;

    @Autowired
    public UserLeadService(final UserRepository userRepository, final LeadService leadService, final HistoryService historyService) {
        this.userRepository = userRepository;
        this.leadService = leadService;
        this.historyService = historyService;
    }

    @Transactional
    public User addLeadToUser(User user, Lead lead) {
        this.leadService.validateLeadFields(lead);

//        CREATE AN INITIAL HISTORY WITH VALUE INTERESTED
        History newHistory = new History();
        newHistory.setStatus("Interested");

        Lead updatedLead = this.historyService.addHistoryToLead(lead, newHistory);
        updatedLead.setUser(user);
        user.getLeads().add(updatedLead);

        userRepository.save(user);
        return user;
    }

}
