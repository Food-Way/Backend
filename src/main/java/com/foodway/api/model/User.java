package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Optional;
import java.util.UUID;

@Entity(name = "tbUser")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID idUser;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated
    private ETypeUser ETypeUser;
    private String profilePhoto;

    public User() {
    }

    public User(String name, String email, String password, ETypeUser ETypeUser, String profilePhoto) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ETypeUser = ETypeUser;
        this.profilePhoto = profilePhoto;
    }

    public abstract void update(Optional<?> optional);

    //todo Relembrar pq passa id
    public abstract void comment(UUID idUser);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ETypeUser getTypeUser() {
        return ETypeUser;
    }

    public void setTypeUser(ETypeUser ETypeUser) {
        this.ETypeUser = ETypeUser;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
