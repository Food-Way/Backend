package com.foodway.api.record;

import com.foodway.api.model.Comment;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record RequestCommentChild (
        @NotBlank
        String comment,
        List<String> images
){}