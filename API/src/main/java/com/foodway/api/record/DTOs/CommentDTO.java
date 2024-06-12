package com.foodway.api.record.DTOs;

import java.util.UUID;

public record CommentDTO(
        String establishmentName,
        String comment,
        Double commentRate,
        int upvotes,
        UUID idEstablishment,
        String photoUrl

) {
}