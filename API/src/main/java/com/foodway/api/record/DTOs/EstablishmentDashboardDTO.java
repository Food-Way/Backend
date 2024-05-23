package com.foodway.api.record.DTOs;

import com.foodway.api.model.Tags;

import java.util.List;

public record EstablishmentDashboardDTO(
        Double generalRate,
        Double ambientRate,
        Double serviceRate,
        Double foodRate,
        List<Tags> tags
) {
}
