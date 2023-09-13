package com.foodway.api.record;

import com.foodway.api.model.ETypeUser;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUserEstablishment(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String password,
        @Enumerated
        @NotNull
        ETypeUser typeUser,
        @NotBlank
        String profilePhoto,
        @NotBlank
        String establishmentName,
        @NotBlank
        String description,
        @NotBlank
        String cep,
        @NotBlank
        String number,
        @NotBlank
        String rate,
        @NotBlank
        String cnpj
) {
}
