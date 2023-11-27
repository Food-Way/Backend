package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "tbProduct")
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idProduct;
    @JsonIgnore
    @ManyToOne
    private Establishment establishment;
    private String name;
    private String description;
    private BigDecimal price;
    private String photo;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }


    public Product() {
    }

    public Product(String name, String description, BigDecimal price, String photo) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
    }

    public Product(RequestProduct requestProduct) {
        this.name = requestProduct.name();
        this.description = requestProduct.description();
        this.price = requestProduct.price();
        this.photo = requestProduct.photo();
    }

    public void update(@NotNull Optional<?> optional) {
        UpdateProductData updateProductData = (UpdateProductData) optional.get();
        this.name = updateProductData.name();
        this.description = updateProductData.description();
        this.price = updateProductData.price();
        this.photo = updateProductData.photo();

    }

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getIdEstablishment() {
        return establishment.getIdUser();
    }

    @Override
    public String toString() {
        return String.format(idProduct +";"+ name +";"+ price +";"+ createdAt +";"+ updatedAt);
    }
}
