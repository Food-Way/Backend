package com.foodway.api.record;

import com.foodway.api.model.ETypeRate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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
