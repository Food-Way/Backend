package com.foodway.api.record;

public record UpdateEstablishmentPersonal(
        String name,
        String email,
        String password,
        String novaPassword
) {

}
