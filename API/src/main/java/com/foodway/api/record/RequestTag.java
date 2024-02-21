package com.foodway.api.record;

import com.foodway.api.model.Enums.ETagName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestTag(
        @NotNull
        UUID idEstablishment,
        @NotNull
        ETagName name,
        @NotNull
        boolean enable
) {
}
