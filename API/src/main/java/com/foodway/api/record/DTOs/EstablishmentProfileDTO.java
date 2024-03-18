package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;

import java.util.List;

public record EstablishmentProfileDTO(
        String name,
        String establishmentName,
        String culinary,
        String email,
        String phone,
        Double generalRate,
        Double foodRate ,
        Double ambientRate,
        Double serviceRate,
        String lat,
        String lng,
        long qtdUpvotes,
        Integer qtdComments,
        long qtdRates,
        List<Comment> comments,
        String profileHeaderImg
) {
}
