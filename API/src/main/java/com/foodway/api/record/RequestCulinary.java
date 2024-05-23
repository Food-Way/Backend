package com.foodway.api.record;

import jakarta.validation.constraints.NotBlank;

public record RequestCulinary(
        @NotBlank String name,
        @NotBlank String photo
) {
}
