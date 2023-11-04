package com.foodway.api.record;
import com.foodway.api.model.Enums.ETypeUser;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestUserCustomer(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String password,
        @Enumerated

        ETypeUser typeUser,
        @NotBlank
        String profilePhoto,
        String cpf,
        @NotBlank
        String bio
) {
}
