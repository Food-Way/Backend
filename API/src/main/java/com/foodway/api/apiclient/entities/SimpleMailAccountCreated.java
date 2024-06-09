package com.foodway.api.apiclient.entities;

import com.foodway.api.model.Enums.ETypeUser;

public class SimpleMailAccountCreated extends SimpleMail {
    public SimpleMailAccountCreated(String name, String establishmentName, String email, ETypeUser typeUser) {
        super(name, establishmentName, email, typeUser);
    }
}

