package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.models.History;
import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.repositories.HistoryRepository;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final LeadRepository leadRepository;

    public HistoryService(final HistoryRepository historyRepository, final LeadRepository leadRepository) {
        this.historyRepository = historyRepository;
        this.leadRepository = leadRepository;
    }

    @Transactional
    public History addHistoryToLead(Lead lead, @Valid History history) {
        List<History> historyList = lead.getHistoryList();
        historyList.add(history);

        lead.setHistoryList(historyList);
        history.setLeadId(lead.getId());

        this.leadRepository.save(lead);
        return this.historyRepository.save(history);
    }

//
}
