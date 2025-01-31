package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(final HistoryService historyService) {
        this.historyService = historyService;
    }



}
