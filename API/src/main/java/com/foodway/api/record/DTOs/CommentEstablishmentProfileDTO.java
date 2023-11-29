package com.foodway.api.record.DTOs;

import java.util.List;
import java.util.UUID;

public record CommentEstablishmentProfileDTO(UUID idComment, String userPhoto, String comment, Double commentRate, int upvotes, List<CommentEstablishmentProfileDTO> childComments) {
}
