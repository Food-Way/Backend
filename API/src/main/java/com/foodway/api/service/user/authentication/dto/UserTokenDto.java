package com.foodway.api.service.user.authentication.dto;

import com.foodway.api.model.Enums.ETypeUser;

import java.util.UUID;

public class UserTokenDto {
    private UUID idUser;
    private String email;
    private String token;

    private ETypeUser typeUser;

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ETypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(ETypeUser typeUser) {
        this.typeUser = typeUser;
    }
}
