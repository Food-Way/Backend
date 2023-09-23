package com.foodway.api.record;

import com.foodway.api.model.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCrypt;

public record UpdateCostumerData(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank @Email(message = "Email inválido") String email,
        @NotBlank String password,
        @NotNull ETypeUser typeUser,
        @NotBlank String profilePhoto,
        @NotBlank String cpf,
        @NotBlank String bio
) {
}
