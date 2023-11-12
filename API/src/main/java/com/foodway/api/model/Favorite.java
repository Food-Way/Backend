package com.foodway.api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFavorite;

    private UUID idCustomer;

    private UUID idEstablishment;

    public Favorite(UUID idCustomer, UUID idEstablishment) {
        this.idCustomer = idCustomer;
        this.idEstablishment = idEstablishment;
    }

    public int getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(int idFavorite) {
        this.idFavorite = idFavorite;
    }

    public UUID getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(UUID idCustomer) {
        this.idCustomer = idCustomer;
    }

    public UUID getIdEstablishment() {
        return idEstablishment;
    }

    public void setIdEstablishment(UUID idEstablishment) {
        this.idEstablishment = idEstablishment;
    }
}
