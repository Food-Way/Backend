package com.foodway.api.controller;
//
//
//import com.foodway.api.model.User;
//import com.foodway.api.record.RequestUserData;
//import com.foodway.api.service.UserService;
//import org.hibernate.validator.constraints.UUID;

//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;

import com.foodway.api.service.UserService;
import com.foodway.api.service.user.authentication.dto.UserCreateDto;
import com.foodway.api.service.user.authentication.dto.UserLoginDto;
import com.foodway.api.service.user.authentication.dto.UserTokenDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

//
//import java.util.List;
//
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        String passwordEncrypt = passwordEncoder.encode(userCreateDto.getPassword());
        userCreateDto.setPassword(passwordEncrypt);

        this.userService.create(userCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        UserTokenDto userTokenDto = this.userService.authenticate(userLoginDto);
        return ResponseEntity.status(200).body(userTokenDto);
    }

    @GetMapping
    public ResponseEntity<Void> get() {
        return ResponseEntity.ok().build();
    }

}
//
//    @GetMapping("/usuarios-teste") ResponseEntity<List<User>> getUsuarios() {
//        return userService.getUsuarios();
//    }
//
//    @GetMapping("/id")
//    public ResponseEntity<User> getUsuario(@PathVariable UUID id) {
//        return userService.getUsuario(id);
//    }
//
//    @GetMapping
//    public ResponseEntity<User> login(@RequestBody @Validated RequestUserLogin data) {
//        return userService.loginUser(data);
//    }
//
//   @PostMapping
//    public ResponseEntity<User> save(@RequestBody @Validated RequestUserData data) {
//        return userService.cadastrar(data);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<User> delete(@PathVariable UUID id) {
//        return userService.deleteUsuario(id);
//    }
//
//    @PutMapping
//    public ResponseEntity<User> put(@PathVariable UUID id, @RequestBody User user) {
//        return userService.putUsuario(id, user);
//    }
//    /*
//        {
//            "nome": "nome",
//            "sobrenome": "sobrenome",
//            "email": "email",
//            "senha": "senha"
//        }
//     */
//
//
//}
