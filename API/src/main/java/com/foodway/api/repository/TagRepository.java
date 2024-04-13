package com.foodway.api.repository;

import com.foodway.api.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tags, Long> {
    Optional<Tags> findByNameIgnoreCase(String name);
}
