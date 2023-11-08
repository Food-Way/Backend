package com.foodway.api.repository;

import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RateRepository extends JpaRepository<Rate, Long> {
//    @Query("""
//            SELECT AVG(r.ratePoint) FROM Rate r WHERE typeRate = ?1 and idEstablishment = ?2
//            """)
//    double avgIndicators(ETypeRate typeRate, UUID idEstablishment);
}
