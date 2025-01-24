package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Lead addLeadToUser(Integer userId, Lead lead) {
        Optional<User> user = userRepository.findById(userId);
        lead.setUser(user.get());
        user.get().getLeads().add(lead);

        return leadRepository.save(lead);
    }

}
