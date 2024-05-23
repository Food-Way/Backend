package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT AVG(r.ratePoint) FROM com.foodway.api.model.Rate r WHERE r.idCustomer = ?1")
    Double getAvgIndicatorCustomer(UUID idCustomer);

    @Query("SELECT AVG(r.ratePoint) FROM com.foodway.api.model.Rate r WHERE r.idEstablishment = ?1 and r.typeRate = ?2")
    Double getAvgIndicatorEstablishment(UUID idEstablishment, ETypeRate typeRate);

    @Query("""
            SELECT
                AVG(CASE WHEN r.typeRate = ?1 THEN r.ratePoint ELSE NULL END) AS AMBIENT,
                AVG(CASE WHEN r.typeRate = ?2 THEN r.ratePoint ELSE NULL END) AS SERVICE,
                AVG(CASE WHEN r.typeRate = ?3 THEN r.ratePoint ELSE NULL END) AS FOOD
            FROM com.foodway.api.model.Rate r
            WHERE r.idEstablishment = ?4
                        """)
    Map<String, Double> getIndicators(ETypeRate ambient, ETypeRate service, ETypeRate food, UUID idEstablishment);


    @Query("""
            SELECT r FROM com.foodway.api.model.Rate r
            WHERE r.idCustomer = ?1 and r.idEstablishment = ?2
            """)
    List<Rate> findByCommentOfCustomer(UUID idCustomer, UUID idEstablishment);

    long countByIdEstablishment(UUID idEstablishment);

}
