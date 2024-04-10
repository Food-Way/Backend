package com.foodway.api.record.DTOs;

import com.foodway.api.model.Tags;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record EstablishmentDashboardViewDTO(
        List<CommentDTO> comments,
        Double generalRate,
        List<EstablishmentRateDto> establishmentRate,
        List<QtdEvaluationDaysForWeek> qtdEvaluationDaysForWeek,
        Set<Tags> tags
) {
}
