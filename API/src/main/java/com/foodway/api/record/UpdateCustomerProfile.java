package com.foodway.api.record;

public record UpdateCustomerProfile(
        String name,
        String profilePhoto,
        String profileHeaderImg,
        String bio
) {
}
