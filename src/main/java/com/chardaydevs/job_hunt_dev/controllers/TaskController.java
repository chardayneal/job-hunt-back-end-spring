package com.chardaydevs.job_hunt_dev.controllers;

import com.chardaydevs.job_hunt_dev.models.Task;
import com.chardaydevs.job_hunt_dev.repositories.TaskRepository;
import com.chardaydevs.job_hunt_dev.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") String id) {
        return this.taskService.validateTask(id);
    }

    @PatchMapping("/{id}")
    public Task updateTask(@PathVariable("id") String id, @RequestBody Task t) {
        this.taskService.validateTaskFields(t);
        return this.taskService.updateTask(id, t);
    }

    @PatchMapping("/{id}/mark_complete")
    public Task markTaskComplete(@PathVariable("id") String id) {
        Task task = this.taskService.validateTask(id);
        task.setComplete(true);
        return this.taskRepository.save(task);
    }

    @PatchMapping("/{id}/mark_incomplete")
    public Task markTaskIncomplete(@PathVariable("id") String id) {
        Task task = this.taskService.validateTask(id);
        task.setComplete(false);
        return this.taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") String id) {
        Task task = this.taskService.validateTask(id);
        this.taskRepository.delete(task);

        return "Task successfully deleted";
    }


}
