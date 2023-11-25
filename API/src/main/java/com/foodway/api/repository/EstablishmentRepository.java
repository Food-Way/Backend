package com.foodway.api.repository;

import com.foodway.api.model.Establishment;
import java.util.List;

import com.foodway.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {

    List<Establishment> findTop10ByOrderByPostListDesc();

    List<Establishment> findTop10ByCulinary_NameOrderByPostListDesc(String culinary);

    List<Establishment> findEstablishmentByCulinary_Id(int id);

    List<Establishment> findByEstablishmentNameContainsIgnoreCase(String establishmentName);

    long countByPostList_UpvoteList_IdEstablishment(UUID idEstablishment);

}
