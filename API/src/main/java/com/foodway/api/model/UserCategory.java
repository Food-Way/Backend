package com.foodway.api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUserCategory;

    private UUID idCategory;

    private UUID idUser;

}
