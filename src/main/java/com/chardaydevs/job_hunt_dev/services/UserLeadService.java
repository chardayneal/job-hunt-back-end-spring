package com.chardaydevs.job_hunt_dev.services;

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

    @Autowired
    public UserLeadService(final UserRepository userRepository, final LeadService leadService) {
        this.userRepository = userRepository;
        this.leadService = leadService;
    }

    @Transactional
    public User addLeadToUser(User user, Lead lead) {
        this.leadService.validateLeadFields(lead);

        lead.setUser(user);
        user.getLeads().add(lead);

        userRepository.save(user);
        return user;
    }

}
