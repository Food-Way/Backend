package com.foodway.api.record;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RequestProduct(
        @NotNull String name,
        String description,
        @NotNull BigDecimal price,
        String photo
) {
}
