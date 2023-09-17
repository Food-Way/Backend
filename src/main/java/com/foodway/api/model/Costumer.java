package com.foodway.api.model;

import com.foodway.api.record.RequestUserCostumer;
import com.foodway.api.record.UpdateCostumerData;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
@Table(name = "tbCostumer")
@Entity(name = "costumer")
@EqualsAndHashCode
public class Costumer extends User{
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID idCostumer;
    @Column(length = 11, unique = true)
    private String cpf;
    @Column(length = 254)
    private String bio;

    public Costumer() {}

    public Costumer(RequestUserCostumer costumer) {
        super(costumer.name(), costumer.email(), costumer.password(), costumer.typeUser(), costumer.profilePhoto());
        this.cpf = costumer.cpf();
        this.bio = costumer.bio();
    }

    public Costumer(String name, String email, String password, ETypeUser typeUser, String profilePhoto, String cpf, String bio) {
        super(name, email, password, typeUser, profilePhoto);
        this.cpf = cpf;
        this.bio = bio;
    }

    @Override
    public void update(@NotNull Optional<?> optional) {
        UpdateCostumerData c = (UpdateCostumerData) optional.get();
        System.out.println(c);
        this.setName(c.name());
        this.setEmail(c.email());
        this.setPassword(c.password());
        this.setProfilePhoto(c.profilePhoto());
        this.setCpf(c.cpf());
        this.setBio(c.bio());
    }

    @Override
    public void comment(UUID idUser) {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    /*
    {
        "name": "string",
        "email": "string",
        "password": "string",
        "ETypeUser": "COSTUMER",
        "profilePhoto": "string",
        "cpf": "string",
        "bio": "string"
    }
    * */






}
