//package com.foodway.api.controller;
//
//
//import com.foodway.api.model.User;
//import com.foodway.api.record.RequestUserData;
//import com.foodway.api.service.UserService;
//import org.hibernate.validator.constraints.UUID;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/usuarios")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
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
