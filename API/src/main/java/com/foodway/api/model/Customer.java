package com.foodway.api.model;

import com.foodway.api.record.UpdateCustomerPersonalInfo;
import com.foodway.api.record.UpdateCustomerProfile;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Table(name = "tbCustomer")
@Entity(name = "customer")
public class Customer extends User {
    @Column(length = 11, unique = true)
    private String cpf;
    @Column(length = 254)
    private String bio;
    private Integer level;
    private Integer xp;
    private Integer xpLimit;
    @OneToMany
    private List<Rate> rates;
    @OneToMany
    private List<Favorite> favorites;

    public Customer() {
        this.rates = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public Customer(RequestUserCustomer customer) {
        super(customer.name(), customer.email(), customer.password(), customer.typeUser(), customer.profilePhoto(), customer.profileHeaderImg(), customer.culinary());
        this.cpf = customer.cpf();
        this.bio = customer.bio();
        this.level = 1;
        this.xp = 0;
        this.xpLimit = 100;
    }

    @Override
    public void update(@NotNull Optional<?> optional) {
        UpdateCustomerData c = (UpdateCustomerData) optional.get();
        this.setName(c.name());
        this.setEmail(c.email());
        this.setPassword(c.password());
        this.setProfilePhoto(c.profilePhoto());
        this.setCpf(c.cpf());
        this.setBio(c.bio());
        this.setCulinary(c.culinary());
        this.setProfileHeaderImg(c.profileHeaderImg());
    }

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getXpLimit() {
        return xpLimit;
    }

    public void setXpLimit(Integer xpLimit) {
        this.xpLimit = xpLimit;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void addRate(Rate rate) {
        this.rates.add(rate);
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void updateProfile(Optional<UpdateCustomerProfile> customer) {
        UpdateCustomerProfile c = customer.get();

        if (c.name() != null && !c.name().isBlank()) {
            this.setName(c.name());
        }

        if (c.bio() != null && !c.bio().isBlank()) {
            this.setBio(c.bio());
        }
        if (c.profilePhoto() != null && !c.profilePhoto().isBlank()) {
            this.setProfilePhoto(c.profilePhoto());
        }

        if (c.profileHeaderImg() != null && !c.profileHeaderImg().isBlank()) {
            this.setProfileHeaderImg(c.profileHeaderImg());
        }
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public void updatePersonalInfo(Optional<UpdateCustomerPersonalInfo> customer) {

        if (customer.get().emailNew() != null && !customer.get().emailNew().isBlank()) {
            this.setEmail(customer.get().emailNew());
        }
        if (customer.get().passwordNew() != null && !customer.get().passwordNew().isBlank()) {
            this.setPassword(encodePassword(customer.get().passwordNew()));
        }

    }

    public void addFavorite(Favorite saved) {
        this.favorites.add(saved);
    }

    public Customer increaseXp(int xp) {
        this.setXp(this.getXp() + xp);
        if (this.getXp() >= getXpLimit()) {
            this.setLevel(this.getLevel() + 1);
            this.setXpLimit(this.getXpLimit() + 100);
            this.setXp(0);
        }
        return this;
    }
}
