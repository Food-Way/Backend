package com.foodway.api.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UpdateCustomerPersonalInfo(

        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String novaSenha,
        @CPF
        String cpf
) {
}
