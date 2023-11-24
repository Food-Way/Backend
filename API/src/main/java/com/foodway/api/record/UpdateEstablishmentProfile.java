package com.foodway.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEstablishmentProfile(
        @NotNull
        String establishmentName,
        @NotNull
        String description,
        @Email
        String email,
        @NotBlank
        String password
        ) {


}
