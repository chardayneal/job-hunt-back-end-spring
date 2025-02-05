package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.History;
import com.chardaydevs.job_hunt_dev.repositories.HistoryRepository;
import com.chardaydevs.job_hunt_dev.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    private final HistoryRepository historyRepository;
    private final HistoryService historyService;

    @Autowired
    public HistoryController(final HistoryRepository historyRepository, final HistoryService historyService) {
        this.historyRepository = historyRepository;
        this.historyService = historyService;
    }

    @GetMapping("/{id}")
    public History getHistoryById(@PathVariable("id") String id) {
        return this.historyService.validateHistory(id);
    }

    @PatchMapping("/{id}")
    public History updateHistory(@PathVariable("id") String id, @RequestBody History h) {
        this.historyService.validateHistoryFields(h);
        return this.historyService.updateHistory(id, h);
    }

    @DeleteMapping("/{id}")
    public String deleteHistory(@PathVariable("id") String id) {
        History history = this.historyService.validateHistory(id);
        this.historyRepository.delete(history);

        return "History successfully deleted";
    }

}
