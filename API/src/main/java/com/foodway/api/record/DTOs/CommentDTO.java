package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;

public record CommentDTO(String establishmentName, String title, String comment, Double commentRate
//                         UpvoteDTO upvote
) {
}
