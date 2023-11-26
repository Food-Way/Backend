package com.foodway.api.record.DTOs;

import com.foodway.api.model.Enums.ETypeUser;

import java.util.UUID;

public record SearchCustomerDTO(
        UUID idCustomer,
        String name,
        ETypeUser typeUser,
        String culinary,
        Double generalRate,
        String bio,
        long upvotes,
        String photo
) {
}
