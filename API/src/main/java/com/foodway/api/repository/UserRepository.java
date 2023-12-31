package com.foodway.api.repository;

import com.foodway.api.model.User;
import com.foodway.api.service.rate.RateService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {
public Optional<User> findByEmail(String email);

}
