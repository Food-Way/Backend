package com.foodway.api.record;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEstablishmentMobileProfile(
        @NotBlank
        String establishmentName,
        @NotNull
        String phone,
        @NotNull
        String description,
        @NotNull
        String profilePhoto
) {

}
