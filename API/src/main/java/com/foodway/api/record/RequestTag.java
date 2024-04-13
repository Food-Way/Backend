package com.foodway.api.record;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestTag(
        @NotNull
        String name

) {
}
