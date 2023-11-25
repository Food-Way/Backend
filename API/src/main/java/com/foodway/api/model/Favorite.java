package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "tbFavorite")
@Entity(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFavorite;
    private UUID idCustomer;
    private UUID idEstablishment;
    @ManyToMany(mappedBy = "favorites")
    List<Customer> customers;
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

    @JsonIgnore
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}