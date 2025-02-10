package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.exception.InvalidIdException;
import com.chardaydevs.job_hunt_dev.exception.InvalidUserException;
import com.chardaydevs.job_hunt_dev.models.Insight;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.InsightRepository;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class InsightService {
    private final InsightRepository insightRepository;
    private final UserRepository userRepository;

    public InsightService(final InsightRepository insightRepository, UserRepository userRepository) {
        this.insightRepository = insightRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Insight validateInsight(String id) throws InvalidUserException, InvalidIdException {
        try {
            UUID userId = UUID.fromString(id);
        } catch (IllegalArgumentException error) {
            throw new InvalidIdException("Id provided is an invalid id");
        }

        Optional<Insight> foundInsight = this.insightRepository.findById(UUID.fromString(id));
        if (foundInsight.isEmpty()) {
            throw new InvalidUserException("Insight does not exist");
        }
        return foundInsight.get();
    }

    @Transactional
    public User addInsightToUser(User user, @Valid Insight insight) {
        insight.setUser(user);
        user.getInsights().add(insight);

        return this.userRepository.save(user);
    }
}