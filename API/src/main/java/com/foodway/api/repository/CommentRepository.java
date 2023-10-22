package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByEstablishment_IdUserOrderByCreatedAtAsc(UUID idUser);
    // TODO fazer filtros de comentários e paginação

}
