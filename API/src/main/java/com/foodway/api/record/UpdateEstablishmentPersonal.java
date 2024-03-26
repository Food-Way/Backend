package com.foodway.api.record;

public record UpdateEstablishmentPersonal(
        String name,
        String establishmentName,
        String emailActual,
        String emailNew,
        String passwordActual,
        String passwordNew,
        String phone,
        String description
) {

}
