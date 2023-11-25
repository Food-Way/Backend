package com.foodway.api.record;

import com.foodway.api.model.Establishment;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
