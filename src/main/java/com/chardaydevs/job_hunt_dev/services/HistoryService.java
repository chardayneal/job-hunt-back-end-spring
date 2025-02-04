package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.exception.InvalidIdException;
import com.chardaydevs.job_hunt_dev.exception.InvalidUserException;
import com.chardaydevs.job_hunt_dev.models.History;
import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.HistoryRepository;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final LeadRepository leadRepository;

    public HistoryService(final HistoryRepository historyRepository, final LeadRepository leadRepository) {
        this.historyRepository = historyRepository;
        this.leadRepository = leadRepository;
    }

    @Transactional
    public History validateHistory(String id) throws InvalidUserException, InvalidIdException {
        try {
            UUID userId = UUID.fromString(id);
        } catch (IllegalArgumentException error) {
            throw new InvalidIdException("Id provided is an invalid id");
        }

        Optional<History> foundHistory = this.historyRepository.findById(UUID.fromString(id));
        if (foundHistory.isEmpty()) {
            throw new InvalidUserException("History does not exist");
        }
        return foundHistory.get();
    }

    @Transactional
    public void validateHistoryFields(History h) {
        if (h == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (h.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (h.getStatus() != null && h.getStatus().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }

    @Transactional
    public Lead addHistoryToLead(Lead lead, @Valid History history) {
        history.setLead(lead);
        lead.getHistoryList().add(history);

        return this.leadRepository.save(lead);

    }

    @Transactional
    public History updateHistory(String id, History h) {
        History history = validateHistory(id);

        if (h.getStatus() != null) {
            history.setStatus(h.getStatus());
        }

        return this.historyRepository.save(history);
    }
}
