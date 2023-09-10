package com.foodway.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "tbEstablishment")
//@NotBlank
@EqualsAndHashCode
public class Establishment extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idEstablishment;
    @Column(length = 75)
    private String establishmentName;
    @Column(length = 255)
    private String description;
    @Column(length = 8)
    private String cep;
    @Column(length = 45)
    private String number;
    private String rate;
    @Column(length = 14)
    private String cnpj;
//    private List<Product> menu;
//    private List<Comment> postList;

    public Establishment() {}

    @Override
    public void update(Optional<?> optional) {
        Establishment establishment = (Establishment) optional.get();
        this.setEstablishmentName(establishment.getEstablishmentName());
        this.setDescription(establishment.getDescription());
        this.setCep(establishment.getCep());
        this.setNumber(establishment.getNumber());
        this.setRate(establishment.getRate());
        this.setCnpj(establishment.getCnpj());
    }

    @Override
    public void comment(UUID idUser) {}

    public Establishment(String name, String email, String password, TypeUser typeUser, String profilePhoto, String establishmentName, String description, String cep, String number, String rate, String cnpj) {
        super(name, email, password, typeUser, profilePhoto);
        this.establishmentName = establishmentName;
        this.description = description;
        this.cep = cep;
        this.number = number;
        this.rate = rate;
        this.cnpj = cnpj;
//        this.menu = menu;
//        this.postList = postList;
    }

    public UUID getIdEstablishment() {
        return idEstablishment;
    }
    public String getEstablishmentName() {
        return establishmentName;
    }
    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


}
