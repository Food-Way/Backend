package com.foodway.api.model;

import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@Table(name = "tbCustomer")
@Entity(name = "customer")
public class Customer extends User {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID idCostumer;
    @Column(length = 11, unique = true)
    private String cpf;
    @Column(length = 254)
    private String bio;
    @OneToMany
    private List<Rate> rates;

    public Customer() {
    }

    public Customer(RequestUserCustomer customer) {
        super(customer.name(), customer.email(), customer.password(), customer.typeUser(), customer.profilePhoto());
        this.cpf = customer.cpf();
        this.bio = customer.bio();
    }

    public Customer(String name, String email, String password, ETypeUser typeUser, String profilePhoto, String cpf, String bio) {
        super(name, email, password, typeUser, profilePhoto);
        this.cpf = cpf;
        this.bio = bio;
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
