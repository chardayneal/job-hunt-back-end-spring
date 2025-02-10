package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.Insight;
import com.chardaydevs.job_hunt_dev.repositories.InsightRepository;
import com.chardaydevs.job_hunt_dev.services.InsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("insights")
@CrossOrigin(origins = "*")
public class InsightController {

    private final InsightService insightService;
    private final InsightRepository insightRepository;

    @Autowired
    public InsightController(InsightService insightService, InsightRepository insightRepository) {
        this.insightService = insightService;
        this.insightRepository = insightRepository;
    }

    @GetMapping("/{id}")
    public Insight getInsightById(@PathVariable("id") String id) {
        return this.insightService.validateInsight(id);
    }

    @DeleteMapping("/{id}")
    public String deleteInsight(@PathVariable("id") String id) {
        Insight insight = this.insightService.validateInsight(id);
        this.insightRepository.delete(insight);

        return "Insight successfully deleted";
    }


}