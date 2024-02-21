package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import com.foodway.api.utils.Fila;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findByIdEstablishment(UUID id);

    Optional<List<Comment>> findTop4ByIdCustomer(UUID idCustomer);

    long countByIdCustomer(UUID idCustomer);

    @Query("""
            SELECT c FROM com.foodway.api.model.Comment c
            WHERE idEstablishment = ?1
            """)
    List<Comment> findByidEstablishment(UUID idEstablishment, PageRequest pageable);
}
