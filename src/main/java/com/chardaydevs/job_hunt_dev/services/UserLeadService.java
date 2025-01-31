package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserLeadService {

    private final UserRepository userRepository;
    private final LeadRepository leadRepository;
    private final LeadService leadService;

    @Autowired
    public UserLeadService(final UserRepository userRepository, final LeadRepository leadRepository, final LeadService leadService) {
        this.userRepository = userRepository;
        this.leadRepository = leadRepository;
        this.leadService = leadService;
    }

    @Transactional
    public Lead addLeadToUser(User user, Lead lead) {
        this.leadService.validateLeadFields(lead);
        List<Lead> userLeads = user.getLeads();
        userLeads.add(lead);

        user.setLeads(userLeads);
        lead.setUserId(user.getId());

        this.userRepository.save(user);
        return this.leadRepository.save(lead);
    }

}
