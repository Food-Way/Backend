package com.foodway.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodway.api.record.RequestUserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
