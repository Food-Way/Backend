package com.foodway.api.record;

import com.foodway.api.model.Comment;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record RequestCommentChild (
        @NotNull
        UUID idCustomer,
        @NotNull
        UUID idEstablishment,
        @NotNull
        UUID idParent,
        @NotBlank
        String comment,
        @NotBlank
        String userPhoto,
        List<String> images
){}