package com.foodway.api.record;

import com.foodway.api.model.Costumer;
import com.foodway.api.model.Rate;
import com.foodway.api.model.Tags;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateCommentData(
        @NotBlank int upvotes,
        @NotBlank String coment,
        @NotBlank List<Tags> tagList,
        @NotBlank List<Costumer> listCostumer,
        @NotBlank List<String> images,
        @NotBlank Rate rate
) {
}
