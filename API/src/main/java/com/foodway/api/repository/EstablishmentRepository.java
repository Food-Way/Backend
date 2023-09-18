package com.foodway.api.repository;

import com.foodway.api.model.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {
}
