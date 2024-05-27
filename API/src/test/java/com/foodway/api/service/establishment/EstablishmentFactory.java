package com.foodway.api.service.establishment;

import com.foodway.api.model.Culinary;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;

import java.util.List;

public class EstablishmentFactory {
    public static UpdateEstablishmentData createUpdateEstablishmentData() {
        return new UpdateEstablishmentData("Marcelo", "marcelo@gmail.com", "12345678", ETypeUser.ESTABLISHMENT,
                "imagem", "profileHeaderImg", "Restaurante do Leleo", "Vendemos Pizza", "11999887766","06990590000980",
                createUpdateEstablishmentDataAddress()
        );
    }

    public static UpdateEstablishmentData.Address createUpdateEstablishmentDataAddress() {
        return new UpdateEstablishmentData.Address("08210560", "123", "Ap B31", "Rua do Marcelo", "Bairro do Marcelo", "Sao Paulo", "SP");
    }

    public static RequestUserEstablishment createRequestUserEstablishment() {
        return new RequestUserEstablishment("Marcelo", "marcelo@gmail.com", "12345678", ETypeUser.ESTABLISHMENT,
                "imagem", "profileHeaderImg", "Restaurante do Leleo", "Vendemos Pizza", "11999887766","06990590000980",
                createUpdateRequestUserEstablishmentAddress(), List.of(new Culinary("imagem", "Mexicana"), new Culinary("imagem", "Brasileira"))
        );
    }

    public static RequestUserEstablishment.Address createUpdateRequestUserEstablishmentAddress() {
        return new RequestUserEstablishment.Address("08210560", "123", "Ap B31", "Rua do Marcelo", "Bairro do Marcelo", "Sao Paulo", "SP");
    }


}
