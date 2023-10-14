package com.foodway.api.record;

//import com.foodway.api.model.Post;
//import com.foodway.api.model.Product;
import com.foodway.api.model.ETypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.security.crypto.bcrypt.BCrypt;

public record UpdateEstablishmentData(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull ETypeUser typeUser,
        @NotBlank String profilePhoto,
        @NotBlank String establishmentName,
        @NotBlank String description,
        @NotBlank String cep,
        @NotBlank String number,
        @NotBlank String complement,
        @NotBlank String rate,
        @NotBlank
        String cnpj
//        List<Product> menu,
//        List<Post> postList
) {
}
