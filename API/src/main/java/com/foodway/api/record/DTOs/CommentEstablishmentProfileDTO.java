package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;

import java.util.List;
import java.util.UUID;

public record CommentEstablishmentProfileDTO(
        UUID idComment,
        String userPhoto,
        String comment,
        Double commentRate,
        int upvotes,
        List<Comment> childComments
) {
}
