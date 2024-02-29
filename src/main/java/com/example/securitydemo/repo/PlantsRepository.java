package com.example.securitydemo.repo;

import com.example.securitydemo.model.Plants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantsRepository extends JpaRepository<Plants, Long> {
}