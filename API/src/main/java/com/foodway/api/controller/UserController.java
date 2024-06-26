package com.foodway.api.controller;

import com.foodway.api.service.UserService;
import com.foodway.api.service.user.authentication.dto.UserCreateDto;
import com.foodway.api.service.user.authentication.dto.UserLoginDto;
import com.foodway.api.service.user.authentication.dto.UserTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Create a new user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the created user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Void> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        String passwordEncrypt = passwordEncoder.encode(userCreateDto.getPassword());
        userCreateDto.setPassword(passwordEncrypt);

        this.userService.create(userCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Realize a user login", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return the user login"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<UserTokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        UserTokenDto userTokenDto = this.userService.authenticate(userLoginDto);
        return ResponseEntity.status(200).body(userTokenDto);
    }

    @GetMapping
    @Operation(summary = "Check if user is authenticated", method = "HTTP GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is authenticated"),
            @ApiResponse(responseCode = "403", description = "User is not authenticated"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred while processing the request")
    })
    public ResponseEntity get() {
        // Se o usuário está autenticado, retorna um código de status 200
        return ResponseEntity.ok().build();
    }
}