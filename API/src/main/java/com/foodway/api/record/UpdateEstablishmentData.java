package com.foodway.api.record;


import com.foodway.api.model.Enums.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEstablishmentData(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull ETypeUser typeUser,
        String profilePhoto,
        String profileHeaderImg,
        @NotBlank String establishmentName,
        String description,
        String phone,
        @NotBlank String cnpj,
        Address address
) {
    public record Address(
            @NotBlank String cep,
            @NotBlank String number,
            String complement,
            @NotBlank String street,
            @NotBlank String neighborhood,
            @NotBlank String city,
            @NotBlank String state
    ) {
    }
}
