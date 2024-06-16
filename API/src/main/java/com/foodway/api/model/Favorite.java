package com.foodway.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "tbFavorite")
@Entity(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFavorite;
    private UUID idCustomer;
    private UUID idEstablishment;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Favorite() {
    }

    public Favorite(UUID idCustomer, UUID idEstablishment) {
        this.idCustomer = idCustomer;
        this.idEstablishment = idEstablishment;
    }

    public int getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(Integer idFavorite) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}