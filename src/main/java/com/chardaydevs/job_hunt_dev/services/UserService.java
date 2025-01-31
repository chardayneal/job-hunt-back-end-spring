package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.exception.InvalidIdException;
import com.chardaydevs.job_hunt_dev.exception.InvalidUserException;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;


@Service
@Validated
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createValidUser(@Valid User user) {
        this.userRepository.save(user);
        return user;
    }

    public User validateUser(String id) {
        try {
            UUID userId = UUID.fromString(id);
        } catch (IllegalArgumentException error) {
            throw new InvalidIdException("Id provided is an invalid id", error);
        }

        Optional<User> foundUser = this.userRepository.findById(UUID.fromString(id));
        if (foundUser.isEmpty()) {
            throw new InvalidUserException("User does not exist");
        }
        return foundUser.get();
    }

}
