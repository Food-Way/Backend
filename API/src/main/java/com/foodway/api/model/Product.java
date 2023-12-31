package com.foodway.api.model;

import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String name;
    private String description;
    private BigDecimal price;
    private String photo;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

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

    //     {
//         "name": "nome do produto",
//         "description": "descrição do produto",
//         "price": 10.00,
//         "updatedAt": "2021-10-10T10:10:10",
//         "photo": "url da foto"
//     }

}
