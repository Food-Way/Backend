package com.foodway.api.record;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
public record RequestComment(
        int upvotes,
        @NotBlank
        String comment,
        List<String> images
//        List<Tags> tagList,
//        List<Costumer> listCostumer,
//        Rate rate
){
}