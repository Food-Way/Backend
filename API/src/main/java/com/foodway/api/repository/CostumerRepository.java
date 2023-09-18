package com.foodway.api.repository;

import com.foodway.api.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CostumerRepository extends JpaRepository<Costumer,UUID> {
}
