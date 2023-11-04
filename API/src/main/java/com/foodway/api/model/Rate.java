package com.foodway.api.model;

import com.foodway.api.record.RequestRate;
import jakarta.persistence.*;

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
    @ManyToOne
    private Customer customer;
    @PositiveOrZero
    @Min(value = 0, message = "Value should be at least 0.")
    @Max(value = 5, message = "Value cannot exceed 5.")
    private int ratePoint;
    private ETypeRate typeRate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Rate() {
    }

    public Rate(Long idRate, int ratePoint, ETypeRate typeRate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idRate = idRate;
        this.ratePoint = ratePoint;
        this.typeRate = typeRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Rate(RequestRate data) {
        this.ratePoint = data.ratePoint();
        this.typeRate = data.typeRate();
        this.createdAt = data.createdAt();
        this.updatedAt = data.updatedAt();
    }

    public void update(Rate data) {
        this.ratePoint = data.ratePoint;
        this.typeRate = data.typeRate;
        this.createdAt = data.createdAt;
        this.updatedAt = data.updatedAt;
    }

    public Long getIdRate() {
        return idRate;
    }

    public void setIdRate(Long idRate) {
        this.idRate = idRate;
    }

    public int getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(int ratePoint) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
