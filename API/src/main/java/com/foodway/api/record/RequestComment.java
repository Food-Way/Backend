package com.foodway.api.record;
import com.foodway.api.model.Enums.ETypeUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestComment(
        @NotNull
        UUID idCustomer,
        @NotNull
        UUID idEstablishment,
        @NotBlank
        String comment,
        @NotBlank
        String userPhoto,
        @NotBlank
        String userName,
        @NotBlank
        ETypeUser typeUser,
        List<String> images
){
}