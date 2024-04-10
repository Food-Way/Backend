package com.foodway.api.record.DTOs;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Tags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String profileHeaderImg,
        Set<Tags> tags
) {
}
