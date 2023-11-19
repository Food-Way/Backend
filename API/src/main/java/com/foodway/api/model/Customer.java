package com.foodway.api.model;

import com.foodway.api.record.UpdateCustomerPersonalInfo;
import com.foodway.api.record.UpdateCustomerProfile;
import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(name = "tbCustomer")
@Entity(name = "customer")
public class Customer extends User {
    @Column(length = 11, unique = true)
    private String cpf;
    @Column(length = 254)
    private String bio;
    @OneToMany
    private List<Rate> rates;
    @NotNull
    private String profileHeaderImg;
    @ManyToMany
    @JoinTable(
            name = "tbFavoriteEstablishment",
            joinColumns = @JoinColumn(name = "idCustomer"),
            inverseJoinColumns = @JoinColumn(name = "idFavorite")
    )
    private List<Favorite> favorites;

    public Customer() {
    }

    public Customer(RequestUserCustomer customer) {
        super(customer.name(), customer.email(), customer.password(), customer.typeUser(), customer.profilePhoto(), customer.culinary());
        this.cpf = customer.cpf();
        this.bio = customer.bio();
        this.profileHeaderImg = customer.profileHeaderImg();
    }

    public Customer(String name, String email, String password, ETypeUser typeUser, String profilePhoto, String cpf, String bio, List<Culinary> culinary, String profileHeaderImg) {
        super(name, email, password, typeUser, profilePhoto, culinary);
        this.cpf = cpf;
        this.bio = bio;
        this.profileHeaderImg = profileHeaderImg;
        this.rates = new ArrayList<>();
    }



    @Override
    public void update(@NotNull Optional<?> optional) {
        UpdateCustomerData c = (UpdateCustomerData) optional.get();
        System.out.println(c);
        this.setName(c.name());
        this.setEmail(c.email());
        this.setPassword(c.password());
        this.setProfilePhoto(c.profilePhoto());
        this.setCpf(c.cpf());
        this.setBio(c.bio());
        this.setCulinary(c.culinary());
        this.setProfileHeaderImg(c.profileHeaderImg());
    }


    public String getProfileHeaderImg() {
        return profileHeaderImg;
    }

    public void setProfileHeaderImg(String profileHeaderImg) {
        this.profileHeaderImg = profileHeaderImg;
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

    public List<Rate> getRates() {
        return rates;
    }

    public void addRate(Rate rate) {
        this.rates.add(rate);
    }

    public boolean validateTypeRate(ETypeRate typeRate, UUID idEstablishment){
        boolean existTypeRate = false;
        switch (typeRate) {
            case AMBIENT, SERVICE, FOOD:
                for(Rate rate: rates) {
                    if(rate.getTypeRate().equals(typeRate) && rate.getIdEstablishment().equals(idEstablishment)){
                        existTypeRate = true;
                        return existTypeRate;
                    }
                }
                break;
        }
        return existTypeRate;
    }

    public void updateProfile(Optional<UpdateCustomerProfile> customer) {
        UpdateCustomerProfile c = customer.get();
        if (c.email() != null && !c.email().isBlank()) {
            this.setEmail(c.email());
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

        if (customer.get().email() != null && !customer.get().email().isBlank()) {
            this.setEmail(customer.get().email());
        }
        if (customer.get().novaSenha() != null && !customer.get().novaSenha().isBlank()) {
            this.setPassword(encodePassword(customer.get().novaSenha()));
        }

    }
}
