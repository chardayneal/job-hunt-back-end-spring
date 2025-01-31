package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.models.Lead;
import com.chardaydevs.job_hunt_dev.models.Task;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.LeadRepository;
import com.chardaydevs.job_hunt_dev.repositories.TaskRepository;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserTaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task addTaskToUser(User user, @Valid Task task) {
        List<Task> userTasks = user.getTasks();
        userTasks.add(task);

        user.setTasks(userTasks);
        task.setUserId(user.getId());

        this.userRepository.save(user);
        return this.taskRepository.save(task);
    }
}
