package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.exception.InvalidIdException;
import com.chardaydevs.job_hunt_dev.exception.InvalidUserException;
import com.chardaydevs.job_hunt_dev.models.Task;
import com.chardaydevs.job_hunt_dev.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task validateTask(String id) throws InvalidUserException, InvalidIdException {
        try {
            UUID userId = UUID.fromString(id);
        } catch (IllegalArgumentException error) {
            throw new InvalidIdException("Id provided is an invalid id");
        }

        Optional<Task> foundTask = this.taskRepository.findById(UUID.fromString(id));
        if (foundTask.isEmpty()) {
            throw new InvalidUserException("Task does not exist");
        }
        return foundTask.get();
    }

    @Transactional
    public void validateTaskFields(Task t) {
        if (t == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (t.getText() != null && t.getText().isEmpty()) {
            throw new IllegalArgumentException("Task text cannot be null or empty");
        }
    }

    @Transactional
    public Task updateTask(String id, Task t) {
        Task task = validateTask(id);

        if (t.getText() != null) {
            task.setText(t.getText());
        }
        if (t.getDate() != null) {
            task.setDate(t.getDate());
        }
        return this.taskRepository.save(task);
    }
}