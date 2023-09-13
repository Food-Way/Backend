package com.foodway.api.record;

import com.foodway.api.model.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUserData(
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotNull ETypeUser ETypeUser,
        @NotBlank String profilePhoto
){
}
