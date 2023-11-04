package com.foodway.api.record;

//import com.foodway.api.model.Post;
//import com.foodway.api.model.Product;

import com.foodway.api.model.Enums.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEstablishmentData(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull ETypeUser typeUser,
        String profilePhoto,
        @NotBlank String establishmentName,
        String description,
        @NotBlank String cep,
        @NotBlank String number,
        String complement,
        Double rate,
        @NotBlank String cnpj
//        List<Product> menu,
//        List<Post> postList
) {
}
