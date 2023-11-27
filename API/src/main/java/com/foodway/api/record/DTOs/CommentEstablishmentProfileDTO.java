package com.foodway.api.record.DTOs;

import java.util.List;

public record CommentEstablishmentProfileDTO(String userPhoto, String comment, Double commentRate, int upvotes, List<CommentEstablishmentProfileDTO> childComments) {
}
