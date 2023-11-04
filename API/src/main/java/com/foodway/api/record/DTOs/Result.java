package com.foodway.api.record.DTOs;

public class Result {

    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "Result{" +
                "geometry=" + geometry +
                '}';
    }
}
