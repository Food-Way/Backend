package com.foodway.api.record;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateProductData(
        @NotNull String name,
        String description,
        @NotNull BigDecimal price,
        LocalDateTime updatedAt,
        String photo
) {
}
