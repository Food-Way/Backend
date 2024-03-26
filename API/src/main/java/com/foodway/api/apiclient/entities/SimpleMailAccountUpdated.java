package com.foodway.api.apiclient.entities;

import com.foodway.api.model.Enums.ETypeUser;

public class SimpleMailAccountUpdated extends SimpleMail {

    private String profilePhoto;
    private String profileHeader;
    private String phone;
    private String description;

    public SimpleMailAccountUpdated(String name, String establishmentName, String email, ETypeUser typeUser, String profilePhoto, String profileHeader, String phone, String description) {
        super(name, establishmentName, email, typeUser);
        this.profilePhoto = profilePhoto;
        this.profileHeader = profileHeader;
        this.phone = phone;
        this.description = description;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfileHeader() {
        return profileHeader;
    }

    public void setProfileHeader(String profileHeader) {
        this.profileHeader = profileHeader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}