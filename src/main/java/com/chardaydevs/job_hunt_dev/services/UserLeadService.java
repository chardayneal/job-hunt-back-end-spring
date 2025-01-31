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

    @Autowired
    public UserLeadService(UserRepository userRepository, LeadRepository leadRepository) {
        this.userRepository = userRepository;
        this.leadRepository = leadRepository;
    }

    @Transactional
    public Lead addLeadToUser(User user, @Valid Lead lead) {
        List<Lead> userLeads = user.getLeads();
        userLeads.add(lead);

        user.setLeads(userLeads);
        lead.setUserId(user.getId());

        this.userRepository.save(user);
        return this.leadRepository.save(lead);
    }

}
