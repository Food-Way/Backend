package com.foodway.api.record;

import jakarta.validation.constraints.NotBlank;

public record RequestState(
        @NotBlank String name,
        @NotBlank String initials
) {
}
