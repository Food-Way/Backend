package com.foodway.api.repository;

import com.foodway.api.model.User;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsById(@NotNull UUID id);
}
