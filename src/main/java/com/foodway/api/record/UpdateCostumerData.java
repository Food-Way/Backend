package com.foodway.api.record;

import com.foodway.api.model.TypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record UpdateCostumerData(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank @Email(message = "Email inválido") String email,
        @NotBlank String password,
        @NotNull TypeUser typeUser,
        @NotBlank String profilePhoto,
        @NotBlank @CPF(message = "CPF inválido") String cpf,
        @NotBlank String bio
) {
}
