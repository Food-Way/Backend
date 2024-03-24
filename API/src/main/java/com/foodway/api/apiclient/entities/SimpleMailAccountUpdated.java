package com.foodway.api.apiclient.entities;

import com.foodway.api.model.Enums.ETypeUser;

public class SimpleMailAccountUpdated extends SimpleMail {
    public SimpleMailAccountUpdated(String name, String establishmentName, String email, ETypeUser typeUser, String profilePhoto, String profileHeader, String phone, String description) {
        super(name, establishmentName, email, typeUser);

    }
}