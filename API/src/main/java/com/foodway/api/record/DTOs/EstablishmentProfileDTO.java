package com.foodway.api.record.DTOs;

import java.util.List;

public record EstablishmentProfileDTO(
        String name,
        String culinary,
        Double generalRate,
        Double foodRate ,
        Double ambientRate,
        Double serviceRate,
        String lat,
        String lng,
        long qtdUpvotes,
        Integer qtdComments,
        long qtdRates,
        List<CommentEstablishmentProfileDTO> comments,
        String profileHeaderImg
) {
}
