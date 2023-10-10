package com.foodway.api.record;

import com.foodway.api.model.Comment;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record RequestCommentChild (
    @Positive
    int upvotes,
    @NotBlank
    String comment,
//  List<Tags> tagList,
//  List<Costumer> listCostumer,
    @NotNull
    List<String> images
//  Rate rate
){}
