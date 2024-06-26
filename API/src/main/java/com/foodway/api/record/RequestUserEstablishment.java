package com.foodway.api.record;

import com.foodway.api.model.Culinary;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.DTOs.CulinaryDTO;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public record RequestUserEstablishment(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password,
        @Enumerated @NotNull ETypeUser typeUser,
        String profilePhoto,
        String profileHeaderImg,
        @NotBlank String establishmentName,
        String description,
        @NotBlank String cnpj,
        String phone,
        Address address,
        List<Culinary> culinary
) {
    public record Address(
            @NotBlank String cep,
            @NotBlank String number,
            String complement,
            @NotBlank String street,
            @NotBlank String neighborhood,
            @NotBlank String city,
            @NotBlank String state) {

    }
}
