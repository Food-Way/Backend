package com.foodway.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UpdateCustomerPersonalInfo(
        @Email
        String emailActual,
        @Email
        String emailNew,
        @NotBlank
        String password,
        String passwordNew
) {
}
