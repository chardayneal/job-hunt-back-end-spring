package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.exception.InvalidIdException;
import com.chardaydevs.job_hunt_dev.exception.InvalidUserException;
import com.chardaydevs.job_hunt_dev.models.History;
import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.Task;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.HistoryRepository;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import com.chardaydevs.job_hunt_dev.repositories.TaskRepository;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(final TaskRepository taskRepository, final UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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

    public void validateTaskFields(Task t) {
        if (t == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (t.getText() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (t.getText() != null && t.getText().isEmpty()) {
            throw new IllegalArgumentException("Task text cannot be null or empty");
        }
        if (t.getComplete() == null ) {
            throw new IllegalArgumentException("Task property 'complete' must be true or false");
        }
    }

    @Transactional
    public Task addTaskToUser(User user, @Valid Task task) {
        List<Task> taskList = user.getTasks();
        taskList.add(task);

        user.setTasks(taskList);
        task.setUserId(user.getId());

        this.userRepository.save(user);
        return this.taskRepository.save(task);
    }

    public Task updateTask(String id, Task t) {
        Task task = validateTask(id);

        if (t.getText() != null) {
            task.setText(t.getText());
        }
        if (t.getComplete() != null) {
            task.setComplete(t.getComplete());
        }
        if (t.getDate() != null) {
            task.setDate(t.getDate());
        }

        return this.taskRepository.save(task);
    }
}