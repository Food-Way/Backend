package com.foodway.api.service.customer;

import com.foodway.api.model.Culinary;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;

import java.util.List;

public class CustomerFactory {

    public static UpdateCustomerData createUpdateCustomerData() {
        return new UpdateCustomerData("Marcelo", "Alcantara", "marcelo@gmail.com", "12345678", ETypeUser.CLIENT, "imagem", "51113818885", "Amo Pizza", List.of(new Culinary("imagem", "Mexicana"), new Culinary("imagem", "Brasileira")), "profileHeaderImg");
    }

    public static RequestUserCustomer createRequestUserCustomer() {
        return new RequestUserCustomer("Marcelo", "marcelo@gmail.com", "12345678", ETypeUser.CLIENT, "imagem", "profileHeaderImg", "51113818885", List.of(new Culinary("imagem", "Mexicana"), new Culinary("imagem", "Brasileira")), "Amo Pizza");
    }
}
