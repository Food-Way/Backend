package com.foodway.api.record.DTOs;

import java.util.List;

public record DashboardDTO(
        List<CommentDTO> comment,
        Double evaluationEstablishment,
        Integer qtdEvaluationForWeek
) {
}
