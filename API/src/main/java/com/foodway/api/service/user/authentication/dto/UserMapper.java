package com.foodway.api.service.user.authentication.dto;


import com.foodway.api.model.Customer;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.User;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.service.establishment.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.foodway.api.model.Enums.EEntity.CUSTOMER;
import static com.foodway.api.model.Enums.EEntity.ESTABLISHMENT;

public class UserMapper {


    public static User of(UserCreateDto userCreateDto) {
        User user = new Customer();

        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());

        return user;
    }

    public static UserTokenDto of(User user, String token, String establishmentName, String culinary) {
        UserTokenDto userTokenDto = new UserTokenDto();
        ETypeUser typeUser = user.getTypeUser();

        if (typeUser == ETypeUser.ESTABLISHMENT) {
            userTokenDto.setEstablishmentName(establishmentName);
            userTokenDto.setCulinary(culinary);
        }

        userTokenDto.setIdUser(user.getIdUser());
        userTokenDto.setEmail(user.getEmail());
        userTokenDto.setToken(token);
        userTokenDto.setName(user.getName());
        userTokenDto.setProfilePhoto(user.getProfilePhoto());
        userTokenDto.setTypeUser(typeUser);
        return userTokenDto;
    }

}
