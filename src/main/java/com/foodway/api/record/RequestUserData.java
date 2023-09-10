package com.foodway.api.record;

import com.foodway.api.model.TypeUser;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUserData(
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotNull TypeUser typeUser,
        @NotBlank String profilePhoto
){
}
