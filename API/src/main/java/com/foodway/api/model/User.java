package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.model.Enums.ETypeUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Table(name = "tbUser")
@Entity(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUser;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated
    private ETypeUser typeUser;
    private String profilePhoto;
    @ManyToMany
    @JoinTable(
            name = "tbUserCulinary",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idCulinary")
    )
    private List<Culinary> culinary;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(String name, String email, String password, ETypeUser typeUser, String profilePhoto, List<Culinary> culinary) {
        this.name = name;
        this.email = email;
        this.password = encodePassword(password);
        this.typeUser = typeUser;
        this.profilePhoto = profilePhoto;
        this.culinary = culinary;
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public abstract void update(Optional<?> optional);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ETypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(ETypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<Culinary> getCulinary() {
        return culinary;
    }

    public void setCulinary(List<Culinary> culinary) {
        this.culinary = culinary;
    }
}