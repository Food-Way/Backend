package com.foodway.api.repository;

import com.foodway.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
