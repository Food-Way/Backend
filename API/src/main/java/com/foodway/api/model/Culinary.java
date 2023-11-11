package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.record.RequestCulinary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "tbCulinary")
public class Culinary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String photo;
    private String name;
    @ManyToMany(mappedBy = "culinary")
    List<User> users;

    public Culinary() {
    }

    public Culinary(String photo, String name) {
        this.photo = photo;
        this.name = name;
    }

    public Culinary(RequestCulinary culinary) {
        this.photo = culinary.photo();
        this.name = culinary.name();
    }

    public void update(@NotNull Optional<?> optional) {
        RequestCulinary c = (RequestCulinary) optional.get();
        this.setPhoto(c.photo());
        this.setName(c.name());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
