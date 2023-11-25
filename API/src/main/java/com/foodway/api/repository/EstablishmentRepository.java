package com.foodway.api.repository;

import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Establishment;
import java.util.List;

import com.foodway.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EstablishmentRepository extends JpaRepository<Establishment,UUID> {

    List<Establishment> findByOrderByPostListDesc();

    List<Establishment> findByCulinary_NameOrderByPostListDesc(String culinary);

    List<Establishment> findEstablishmentByCulinary_Id(int id);

    List<Product> findProductsByIdUser(UUID idEstablishment);

    List<Establishment> findByEstablishmentNameContainsIgnoreCase(String establishmentName);



    long countByPostList_UpvoteList_IdEstablishment(UUID idEstablishment);


}
