package com.foodway.api.repository;

import com.foodway.api.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {

    boolean existsByIdCommentAndIdCustomer(UUID idComment, UUID idCustomer);

    @Query("SELECT COUNT(u.idComment) FROM com.foodway.api.model.Upvote u WHERE u.idComment = ?1")
    Integer countUpvotesByComment(UUID idPost);

    long countByIdEstablishment(UUID idEstablishment);

    long countByIdCustomer(UUID idCustomer);
}
