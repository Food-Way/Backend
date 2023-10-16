package com.foodway.api.record;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
public record RequestComment(
        @NotNull
        int upvotes,
        @NotBlank
        String comment,
//            List<Tags> tagList,
//
//            List<Costumer> listCostumer,
        @NotNull
        List<String> images
//            Rate rate
){
}