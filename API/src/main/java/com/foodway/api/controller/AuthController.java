//package com.foodway.api.controller;
//import com.foodway.api.config.security.jwt.ManagerToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//        private final ManagerToken managerToken;
//
//        public AuthController(ManagerToken managerToken) {
//            this.managerToken = managerToken;
//        }
//
//        @GetMapping("/generate-token")
//        public String generateToken() {
//            // Obtenha a autenticação atual do contexto de segurança
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            // Gere um novo token
//            String token = managerToken.generateToken(authentication);
//
//            // Registre o token no log
//            System.out.println("Novo token gerado: " + token);
//
//            return token;
//        }
//    }
//
//
