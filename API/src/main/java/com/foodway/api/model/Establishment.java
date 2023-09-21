package com.foodway.api.model;

import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Table(name = "tbEstablishment")
@Entity(name = "establishment")
public class Establishment extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID idEstablishment;
    @Column(length = 75)
    private String establishmentName;
    @Column(length = 255)
    private String description;
    @Column(length = 8)
    private String cep;
    @Column(length = 45)
    private String number;
    private String rate;
    @Column(length = 14, unique = true)
    private String cnpj;
//    private List<Product> menu;
//    private List<Comment> postList;

    public Establishment() {}

    @Override
    public void update(Optional<?> optional) {
        super.setName(((UpdateEstablishmentData) optional.get()).name());
        super.setEmail(((UpdateEstablishmentData) optional.get()).email());
        super.setPassword(((UpdateEstablishmentData) optional.get()).password());
        super.setTypeUser(((UpdateEstablishmentData) optional.get()).typeUser());
        super.setProfilePhoto(((UpdateEstablishmentData) optional.get()).profilePhoto());
        this.establishmentName = ((UpdateEstablishmentData) optional.get()).establishmentName();
        this.description = ((UpdateEstablishmentData) optional.get()).description();
        this.cep = ((UpdateEstablishmentData) optional.get()).cep();
        this.number = ((UpdateEstablishmentData) optional.get()).number();
        this.rate = ((UpdateEstablishmentData) optional.get()).rate();
        this.cnpj = ((UpdateEstablishmentData) optional.get()).cnpj();
    }

    public Establishment(RequestUserEstablishment establishment) {
        super(establishment.name(), establishment.email(), establishment.password(), establishment.typeUser(), establishment.profilePhoto());
        this.establishmentName = establishment.establishmentName();
        this.description = establishment.description();
        this.cep = establishment.cep();
        this.number = establishment.number();
        this.rate = establishment.rate();
        this.cnpj = establishment.cnpj();
    }



    @Override
    public void comment(UUID idUser) {}

    public Establishment(String name, String email, String password, ETypeUser typeUser, String profilePhoto, String establishmentName, String description, String cep, String number, String rate, String cnpj) {
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

//    public UUID getIdEstablishment() {
//        return idEstablishment;
//    }
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



//    public List<Product> getMenu() {
//        return menu;
//    }
//
//    public void setMenu(List<Product> menu) {
//        this.menu = menu;
//    }
//
//    public List<Comment> getPostList() {
//        return postList;
//    }
//
//    public void setPostList(List<Comment> postList) {
//        this.postList = postList;
//    }
//
//    public void addComment(Comment comment) {
//        this.postList.add(comment);
//    }

//    {
//        "name": "leleo",
//            "email": "leleo@gmail.com",
//            "password": "leleooooo",
//            "typeUser": "ESTABLISHMENT",
//            "profilePhoto": "foto",
//            "establishmentName": "leleo da cocada preta",
//            "description": "Um otimo cachorro quente",
//            "cep": "01234568",
//            "number": "451",
//            "rate": "3.5",
//            "cnpj": "12345678912",
//            "menu": [],
//        "postList": []
//    }





}