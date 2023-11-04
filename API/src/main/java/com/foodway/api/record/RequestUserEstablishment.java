package com.foodway.api.record;

import com.foodway.api.model.Enums.ETypeUser;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record RequestUserEstablishment(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password,
        @Enumerated @NotNull ETypeUser typeUser,
        String profilePhoto,
        @NotBlank String establishmentName,
        String description,
        @NotBlank @CNPJ String cnpj,
        Address address

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
