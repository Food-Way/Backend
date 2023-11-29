package com.foodway.api.record;

public record UpdateEstablishmentProfile(
        String emailActual,
        String passwordActual,
        String profilePhoto,
        String profileHeaderImg
) {
}
