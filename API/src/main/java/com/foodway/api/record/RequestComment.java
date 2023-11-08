package com.foodway.api.record;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public record RequestComment(
        int upvotes,
        @NotBlank
        String comment,
        UUID idCustomer,
        List<String> images
//        List<Tags> tagList,
//        List<Costumer> listCostumer,
//        Rate rate
){
}