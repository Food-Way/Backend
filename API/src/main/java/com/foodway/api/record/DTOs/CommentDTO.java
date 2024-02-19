package com.foodway.api.record.DTOs;


public record CommentDTO(String establishmentName, String comment, String commentComment, Double commentRate, int upvotes
) {
}
