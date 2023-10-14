package com.foodway.api.repository;

import com.foodway.api.model.Establishment;
import com.foodway.api.utils.ListaObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {
}
