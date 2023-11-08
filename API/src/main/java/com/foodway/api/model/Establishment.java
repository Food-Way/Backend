package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
import java.util.Optional;

@Table(name = "tbEstablishment")
@Entity(name = "establishment")
public class Establishment extends User {
    @Column(length = 75)
    private String establishmentName;
    @Column(length = 255)
    private String description;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double generalRate;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double ambientRate;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double serviceRate;
    @PositiveOrZero
    @Max(value = 5)
    @Min(value = 0)
    private Double foodRate;
    @Column(length = 14, unique = true)
    private String cnpj;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @JsonIgnore
    @OneToMany
    private List<Rate> rates;
    @JsonIgnore
    @OneToMany
    private List<Comment> postList;


    public Establishment() {
    }

    public Establishment(RequestUserEstablishment establishment) {
        super(establishment.name(), establishment.email(), establishment.password(), establishment.typeUser(), establishment.profilePhoto(), establishment.culinary());
        this.establishmentName = establishment.establishmentName();
        this.description = establishment.description();
        this.address = new Address(establishment.address().cep(), establishment.address().number(), establishment.address().complement(), establishment.address().street(), establishment.address().neighborhood(), establishment.address().city(), establishment.address().state());
        this.cnpj = establishment.cnpj();
    }

    public Establishment(String name, String email, String password, ETypeUser typeUser, String profilePhoto,
                         List<Culinary> culinary, String establishmentName, String description, String cnpj, Address address) {
        super(name, email, password, typeUser, profilePhoto, culinary);
        this.establishmentName = establishmentName;
        this.description = description;
        this.cnpj = cnpj;
        this.address = address;
    }

    @Override
    public void update(Optional<?> optional) {
        super.setName(((UpdateEstablishmentData) optional.get()).name());
        super.setEmail(((UpdateEstablishmentData) optional.get()).email());
        super.setPassword(((UpdateEstablishmentData) optional.get()).password());
        super.setTypeUser(((UpdateEstablishmentData) optional.get()).typeUser());
        super.setProfilePhoto(((UpdateEstablishmentData) optional.get()).profilePhoto());
        this.establishmentName = ((UpdateEstablishmentData) optional.get()).establishmentName();
        this.description = ((UpdateEstablishmentData) optional.get()).description();
        this.address.setCep(((UpdateEstablishmentData) optional.get()).address().cep());
        this.address.setNumber(((UpdateEstablishmentData) optional.get()).address().number());
        this.address.setComplement(((UpdateEstablishmentData) optional.get()).address().complement());
        this.address.setStreet(((UpdateEstablishmentData) optional.get()).address().street());
        this.address.setNeighborhood(((UpdateEstablishmentData) optional.get()).address().neighborhood());
        this.address.setCity(((UpdateEstablishmentData) optional.get()).address().city());
        this.address.setState(((UpdateEstablishmentData) optional.get()).address().state());
        this.cnpj = ((UpdateEstablishmentData) optional.get()).cnpj();
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

    public Double getGeneralRate() {
        return generalRate;
    }

    public void setGeneralRate(Double generalRate) {
        this.generalRate = generalRate;
    }

    public List<Comment> getPostList() {
        return postList;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void addRate(Rate rate) {
        this.rates.add(rate);
    }

    public void setPostList(List<Comment> postList) {
        this.postList = postList;
    }

    public void addComment(Comment comment) {
        this.postList.add(comment);
    }

    public Double getAmbientRate() {
        return ambientRate;
    }

    public void setAmbientRate(Double ambientRate) {
        this.ambientRate = ambientRate;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Double getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(Double foodRate) {
        this.foodRate = foodRate;
    }
}