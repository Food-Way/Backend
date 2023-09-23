package com.foodway.api.record;
import com.foodway.api.model.ETypeUser;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCrypt;

public record RequestUserCostumer(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String password,
        @Enumerated

        ETypeUser typeUser,
        @NotBlank
        String profilePhoto,
        String cpf,
        @NotBlank
        String bio
) {
}
