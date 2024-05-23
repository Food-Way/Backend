package com.foodway.api.repository;

import com.foodway.api.model.Culinary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CulinaryRepository extends JpaRepository<Culinary, Integer> {

}
