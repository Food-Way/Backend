package com.foodway.api.service.user.authentication.dto;

import com.foodway.api.model.Enums.ETypeUser;

import java.util.UUID;

public class UserTokenDto {
    private UUID idUser;
    private String name;
    private String email;
    private String token;
    private String profilePhoto;
    private String profileHeaderImg;
    private ETypeUser typeUser;
    private String establishmentName;
    private String culinary;

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfileHeaderImg() {
        return profileHeaderImg;
    }

    public void setProfileHeaderImg(String profileHeaderImg) {
        this.profileHeaderImg = profileHeaderImg;
    }

    public ETypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(ETypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getCulinary() {
        return culinary;
    }

    public void setCulinary(String culinary) {
        this.culinary = culinary;
    }
}
