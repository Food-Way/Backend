package com.foodway.api.record;

//import com.foodway.api.model.Post;
//import com.foodway.api.model.Product;
import com.foodway.api.model.TypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public record UpdateEstablishmentData(
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotNull TypeUser typeUser,
        @NotBlank String profilePhoto,
        @NotBlank String establishmentName,
        @NotBlank String description,
        @NotBlank String cep,
        @NotBlank String number,
        @NotBlank String rate,
        @NotBlank @CNPJ(message = "CNPJ inválido") String cnpj
//        List<Product> menu,
//        List<Post> postList
) {
}
