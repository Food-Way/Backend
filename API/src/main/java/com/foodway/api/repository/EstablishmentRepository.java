package com.foodway.api.repository;

import com.foodway.api.model.Establishment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {

//    @Query("SELECT e FROM Establishment e WHERE e.culinary.name = ?1 ORDER BY e.rate DESC LIMIT 3")
    List<Establishment> findTop3ByCulinary_NameOrderByRateDesc(String culinary);
}
