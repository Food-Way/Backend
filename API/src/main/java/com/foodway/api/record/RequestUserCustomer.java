package com.foodway.api.record;
import com.foodway.api.model.Culinary;
import com.foodway.api.model.Enums.ETypeUser;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RequestUserCustomer(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String password,
        @Enumerated
        ETypeUser typeUser,
        String profilePhoto,
        String cpf,
        List<Culinary> culinary,
        String bio
) {
}
