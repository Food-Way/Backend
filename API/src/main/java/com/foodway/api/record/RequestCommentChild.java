package com.foodway.api.record;

import com.foodway.api.model.Comment;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestCommentChild (
    @NotNull
    UUID idParent,
    @Negative
    int upvotes,
    @NotBlank
    String coment,
//  List<Tags> tagList,
//  List<Costumer> listCostumer,
    @NotNull
    List<String> images
//  Rate rate
){}
