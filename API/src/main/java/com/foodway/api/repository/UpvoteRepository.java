package com.foodway.api.repository;

import com.foodway.api.model.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpvoteRepository extends JpaRepository<Upvote, Integer> {

}
