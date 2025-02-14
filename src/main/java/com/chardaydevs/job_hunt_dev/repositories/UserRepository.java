package com.chardaydevs.job_hunt_dev.repositories;

import com.chardaydevs.job_hunt_dev.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String string);
    Optional<User> findByToken(UUID token);
}
