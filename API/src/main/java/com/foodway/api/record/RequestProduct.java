package com.foodway.api.record;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record RequestProduct(
        @NotNull String name,
        String description,
        @NotNull BigDecimal price,
        String photo,
        @NotNull
        UUID idEstablishment
) {
}
