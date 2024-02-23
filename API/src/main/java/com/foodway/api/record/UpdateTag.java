package com.foodway.api.record;

import com.foodway.api.model.Enums.ETagName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTag(
        @NotBlank
        ETagName name,
        @NotNull
        boolean enable
) {

}
