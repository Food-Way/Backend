package com.foodway.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestUserLogin(
        @Email String email,
        @NotBlank String senha
) {
}
