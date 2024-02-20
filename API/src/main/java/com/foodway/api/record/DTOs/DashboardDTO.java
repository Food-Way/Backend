package com.foodway.api.record.DTOs;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public record DashboardDTO(
        List<CommentDTO> comments,
        EstablishmentDashboardDTO establishment,
        Map<String, Integer> qtdEvaluationDaysForWeek
) {
}
