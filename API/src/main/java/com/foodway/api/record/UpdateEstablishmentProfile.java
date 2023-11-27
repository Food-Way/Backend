package com.foodway.api.record;

public record UpdateEstablishmentProfile(
        String establishmentName,
        String description,
        String email,
        String password,
        String profilePhoto,
        String profileHeaderImg,
        String phone
) {
}
