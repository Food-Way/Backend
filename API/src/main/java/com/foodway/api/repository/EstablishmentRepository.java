package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Establishment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {

    List<Establishment> findTop3ByOrderByRateDesc();

//    @Query("SELECT e FROM Establishment e WHERE e.culinary.name = ?1 ORDER BY e.rate DESC LIMIT 3")
    List<Establishment> findTop3ByCulinary_NameOrderByRateDesc(String culinary);

    List<Establishment> findByOrderByPostListDesc();

    List<Establishment> findByCulinary_NameOrderByPostListDesc(String culinary);


}
