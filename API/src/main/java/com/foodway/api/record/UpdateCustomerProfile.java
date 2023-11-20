package com.foodway.api.record;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UpdateCustomerProfile(
        
        String name,
         
        String profilePhoto,
         
        String profileHeaderImg,
         
        String password,

        String email,
         
        String bio
) {
}
