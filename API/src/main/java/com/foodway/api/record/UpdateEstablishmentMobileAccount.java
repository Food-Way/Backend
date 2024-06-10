package com.foodway.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEstablishmentMobileAccount(
    @NotBlank
    String name,
    @Email
    String email,
    @Email
    String newEmail,
    @NotBlank
    String password,
    @NotBlank
    String newPassword,
    @NotBlank
    String cep
) {

}
