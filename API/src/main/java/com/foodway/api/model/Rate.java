package com.foodway.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "tbRate")
@Entity(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRate;
    private int ratePoint;
    private ETypeRate typeRate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Rate() {
    }

    public Rate(int ratePoint, ETypeRate typeRate) {
        this.ratePoint = ratePoint;
        this.typeRate = typeRate;
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
}
