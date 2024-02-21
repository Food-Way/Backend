package com.foodway.api.record.DTOs;

public record CommentDTO(
        String establishmentName,
        String comment,
        Double commentRate,
        int upvotes
) {
}