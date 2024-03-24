package com.foodway.api.repository;

import com.foodway.api.model.Establishment;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {

    List<Establishment> findTop10ByOrderByPostListDesc();

    List<Establishment> findTop10ByCulinary_NameOrderByPostListDesc(String culinary);

    List<Establishment> findEstablishmentByCulinary_Id(int id);

    List<Establishment> findByEstablishmentNameContainsIgnoreCase(String establishmentName);

    long countByPostList_UpvoteList_IdEstablishment(UUID idEstablishment);

    List<Establishment> findByEstablishmentNameContainsIgnoreCaseOrderByPostListDesc(String establishmentName);

    List<Establishment> findByEstablishmentNameContainsIgnoreCaseOrderByGeneralRateDesc(String establishmentName);

    List<Establishment> findByEstablishmentNameContainsIgnoreCaseOrderByPostList_UpvoteListDesc(String establishmentName);

    @Query("""
            SELECT e FROM establishment e
            ORDER BY SIZE(e.postList) DESC
            """)
    List<Establishment> findAllByOrderByPostListSizeDesc();

    List<Establishment> findByOrderByGeneralRateDesc();

    List<Establishment> findByOrderByPostList_UpvoteListDesc();

    List<Establishment> findTop10ByCulinary_NameIgnoreCaseOrderByGeneralRate(String culinary);






}
