package com.foodway.api.record;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestComment(
        @NotBlank
        String comment,
        List<String> images
){
}