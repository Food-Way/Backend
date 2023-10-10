package com.foodway.api.record;

import com.foodway.api.model.Costumer;
import com.foodway.api.model.Rate;
import com.foodway.api.model.Tags;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.swing.text.html.HTML;
import java.util.List;

public record RequestComment(
            @NotNull
            int upvotes,
            @NotBlank
            String coment,
            @NotNull
            List<Tags> tagList,
            @NotNull
            List<Costumer> listCostumer,
            @NotNull
            List<String> images,
            @NotNull
            Rate rate
){

}
