package com.chardaydevs.job_hunt_dev.repositories;

import com.chardaydevs.job_hunt_dev.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
}
