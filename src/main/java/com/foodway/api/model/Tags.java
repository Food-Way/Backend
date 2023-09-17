package com.foodway.api.model;

import jakarta.persistence.*;

@Table(name = "tbRate")
@Entity(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTags;

    private String name;

    public Tags(String name) {
        this.name = name;
    }

    public Tags() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
