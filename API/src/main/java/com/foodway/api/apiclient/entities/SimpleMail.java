package com.foodway.api.apiclient.entities;

import com.foodway.api.model.Enums.ETypeUser;

public abstract class SimpleMail {
    private String name;
    private String establishmentName;
    private String email;
    private ETypeUser typeUser;

    public SimpleMail(String name, String establishmentName, String email, ETypeUser typeUser) {
        this.name = name;
        this.establishmentName = establishmentName;
        this.email = email;
        this.typeUser = typeUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ETypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(ETypeUser typeUser) {
        this.typeUser = typeUser;
    }
}

