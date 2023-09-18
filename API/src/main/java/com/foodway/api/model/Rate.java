package com.foodway.api.model;

import jakarta.persistence.*;

@Table(name = "tbRate")
@Entity(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRate;

    private int ratePoint;
    private ETypeRate typeRate;

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
