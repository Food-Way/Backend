package com.foodway.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "FoodWay API", version = "1.0", description = "Documentation FoodWay API v1.0"), servers = {@Server(url = "http://localhost:8080", description = "Base URL API FoodWay")})
@SecurityRequirement(name = "Bearer Authentication")
@EnableFeignClients
public class ApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);

	}
}