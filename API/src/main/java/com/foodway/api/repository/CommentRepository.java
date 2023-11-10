package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findByIdEstablishment(UUID id);

    long countByIdCustomer(UUID idCustomer);
}
