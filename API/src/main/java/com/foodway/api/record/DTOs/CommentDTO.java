package com.foodway.api.record.DTOs;


public record CommentDTO(String establishmentName, String title, String comment, Double commentRate, int upvotes
) {
}
