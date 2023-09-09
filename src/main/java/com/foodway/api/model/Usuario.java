package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.record.RequestUserData;
import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor

public class Usuario implements Updatable {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID idUser;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated
    private TypeUser typeUser;

    public Usuario(RequestUserData data){
        this.name = data.nome() + " " + data.sobrenome();
        this.email = data.email();
        this.password = data.senha();
        this.typeUser = data.typeUser();
    }

    @Override
    public void updateName(String name) {
    }
    public void atualizar( Usuario usuario) {
        this.name = usuario.getName();
        this.email = usuario.getEmail();
        this.typeUser = usuario.getTypeUser();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }
}
