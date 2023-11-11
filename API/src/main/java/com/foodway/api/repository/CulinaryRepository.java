package com.foodway.api.repository;

import com.foodway.api.model.Culinary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CulinaryRepository extends JpaRepository<Culinary, Integer> {
}
