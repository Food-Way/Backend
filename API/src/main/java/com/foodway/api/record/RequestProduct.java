package com.foodway.api.record;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RequestProduct(
        @NotNull String name,
        @NotNull String description,
        @NotNull BigDecimal price,
        @NotNull LocalDateTime updatedAt,
        @NotNull String photo
) {
}
