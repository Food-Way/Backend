package com.foodway.api.record;

import com.foodway.api.model.Enums.ETypeRate;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestRate(
        @NotNull
        int ratePoint,
        @NotNull
        ETypeRate typeRate,
        @NotNull
        LocalDateTime createdAt,
        @NotNull
        LocalDateTime updatedAt
) {}
