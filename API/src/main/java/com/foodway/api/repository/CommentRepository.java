package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findByIdEstablishment(UUID id);

    Optional<List<Comment>> findTop4ByIdCustomer(UUID idCustomer);

    long countByIdCustomer(UUID idCustomer);

}
