package com.foodway.api.apiclient;

import com.foodway.api.model.Enums.ETypeUser;

public record SimpleMail(
        String name,
        String establishmentName,
        String email,
        ETypeUser typeUser
) {
}

