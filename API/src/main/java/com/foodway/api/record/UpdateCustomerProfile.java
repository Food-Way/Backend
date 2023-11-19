package com.foodway.api.record;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UpdateCustomerProfile(
        @NotBlank
        String name,
        @NotBlank
        String profilePhoto,
        @NotBlank
        String profileHeaderImg,
        @NotBlank
        String password,

        @Email
        String email,
        @NotBlank
        String bio
) {
}
