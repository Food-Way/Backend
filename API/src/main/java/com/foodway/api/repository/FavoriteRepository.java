package com.foodway.api.repository;

import com.foodway.api.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<List<Favorite>> findTop4ByIdCustomer(UUID idCustomer);


}
