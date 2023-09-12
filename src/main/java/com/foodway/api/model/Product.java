package com.foodway.api.model;

import com.foodway.api.record.RequestProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "tbProduct")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idProduct;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime updatedAt;
    private String photo;

    public Product() {
    }

    public Product(String name, String description, BigDecimal price, LocalDateTime updatedAt, String photo) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.updatedAt = updatedAt;
        this.photo = photo;
    }

    public Product(RequestProduct requestProduct){
        this.name = requestProduct.name();
        this.description = requestProduct.description();
        this.price = requestProduct.price();
        this.updatedAt = requestProduct.updatedAt();
        this.photo = requestProduct.photo();
    }

    public void update(@NotNull Optional<?> optional) {

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // {
    //     "name": "nome do produto",
    //     "description": "descrição do produto",
    //     "price": 10.00,
    //     "updatedAt": "2021-10-10T10:10:10",
    //     "photo": "url da foto"
    // }

}
