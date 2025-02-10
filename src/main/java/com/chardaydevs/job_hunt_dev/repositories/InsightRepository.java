package com.chardaydevs.job_hunt_dev.repositories;

import com.chardaydevs.job_hunt_dev.models.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsightRepository extends JpaRepository<Insight, UUID> {
}
