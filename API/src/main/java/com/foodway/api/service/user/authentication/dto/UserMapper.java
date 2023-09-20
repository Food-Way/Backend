package com.foodway.api.service.user.authentication.dto;


import com.foodway.api.model.Costumer;
import com.foodway.api.model.User;

public class UserMapper {
    public static User of(UserCreateDto userCreateDto) {
        User user = new Costumer();

        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());

        return user;
    }

    public static  UserTokenDto of(User user, String token){
        UserTokenDto userTokenDto = new UserTokenDto();

        userTokenDto.setEmail(user.getEmail());
        userTokenDto.setToken(token);
        return  userTokenDto;
    }

}
