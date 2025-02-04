package com.chardaydevs.job_hunt_dev.repositories;

import com.chardaydevs.job_hunt_dev.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String string);
}
