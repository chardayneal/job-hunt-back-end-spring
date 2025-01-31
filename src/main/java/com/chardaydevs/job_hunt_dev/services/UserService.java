package com.chardaydevs.job_hunt_dev.services;

import com.chardaydevs.job_hunt_dev.exception.InvalidIdException;
import com.chardaydevs.job_hunt_dev.exception.InvalidUserException;
import com.chardaydevs.job_hunt_dev.models.User;
import com.chardaydevs.job_hunt_dev.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

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

    public User validateUser(String id) throws InvalidUserException, InvalidIdException {
        try {
            UUID userId = UUID.fromString(id);
        } catch (IllegalArgumentException error) {
            throw new InvalidIdException("Id provided is an invalid id");
        }

        Optional<User> foundUser = this.userRepository.findById(UUID.fromString(id));
        if (foundUser.isEmpty()) {
            throw new InvalidUserException("User does not exist");
        }
        return foundUser.get();
    }

    public void validateUserFields(String id, User u) {
        User user = validateUser(id);

        if (u == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (u.getName() == null & u.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }

        if (u.getName() != null && u.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (u.getEmail() != null && !isValidEmail(u.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public User updateUser(String id, User u) {
        User user = validateUser(id);

        if (u.getName() != null) {
            user.setName(u.getName());
        }
        if (u.getEmail() != null) {
            user.setEmail(u.getEmail());
        }
        return this.userRepository.save(user);
    }

}
