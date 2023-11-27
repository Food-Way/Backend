package com.foodway.api.service.user.authentication.dto;


import com.foodway.api.model.Customer;
import com.foodway.api.model.User;

public class UserMapper {
    public static User of(UserCreateDto userCreateDto) {
        User user = new Customer();

        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());

        return user;
    }

    public static  UserTokenDto of(User user, String token){
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setIdUser(user.getIdUser());
        userTokenDto.setEmail(user.getEmail());
        userTokenDto.setToken(token);
        userTokenDto.setName(user.getName());
        userTokenDto.setProfilePhoto(user.getProfilePhoto());
        userTokenDto.setTypeUser(user.getTypeUser());
        return userTokenDto;
    }

}
