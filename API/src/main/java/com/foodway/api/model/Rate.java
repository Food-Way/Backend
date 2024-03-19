package com.foodway.api.model;

import com.foodway.api.record.RequestRate;

import com.foodway.api.model.Enums.ETypeRate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "tbRate")
@Entity(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRate;
    private UUID idCustomer;
    private UUID idEstablishment;
    @PositiveOrZero
    @Min(value = 0, message = "Value should be at least 0.")
    @Max(value = 5, message = "Value cannot exceed 5.")
    private Double ratePoint;
    private ETypeRate typeRate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Rate() {
    }

    public Rate(Double ratePoint, ETypeRate typeRate) {
        this.ratePoint = ratePoint;
        this.typeRate = typeRate;
    }

    public Rate(RequestRate data) {
        this.idCustomer = data.idCustomer();
        this.idEstablishment = data.idEstablishment();
        this.ratePoint = data.ratePoint();
        this.typeRate = data.typeRate();
    }

    public Rate(UUID idCustomer, UUID idEstablishment, Double ratePoint, ETypeRate typeRate) {
        this.idCustomer = idCustomer;
        this.idEstablishment = idEstablishment;
        this.ratePoint = ratePoint;
        this.typeRate = typeRate;
    }

    public void update(Rate data) {
        this.ratePoint = data.ratePoint;
        this.typeRate = data.typeRate;
    }

    public Long getIdRate() {
        return idRate;
    }

    public void setIdRate(Long idRate) {
        this.idRate = idRate;
    }

    public Double getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(Double ratePoint) {
        this.ratePoint = ratePoint;
    }

    public ETypeRate getTypeRate() {
        return typeRate;
    }

    public void setTypeRate(ETypeRate typeRate) {
        this.typeRate = typeRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
