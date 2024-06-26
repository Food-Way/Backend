package com.foodway.api.record;

import com.foodway.api.model.Culinary;
import com.foodway.api.model.Enums.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record UpdateCustomerData(
        String name,
        String lastName,
        @Email(message = "Email inválido") String email,
        @NotBlank String password,
        @NotNull ETypeUser typeUser,
        String profilePhoto,
        @NotBlank @CPF String cpf,
        String bio,
        List<Culinary> culinary,
        @NotNull
        String profileHeaderImg
) {
}
