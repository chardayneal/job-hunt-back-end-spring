package com.chardaydevs.job_hunt_dev.repositories;

import com.chardaydevs.job_hunt_dev.models.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer> {
}
