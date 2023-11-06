package com.foodway.api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbCulinary")
public class Culinary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @ManyToMany(mappedBy = "culinary")
    List<User> users;

    public Culinary() {
    }

    public Culinary(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
