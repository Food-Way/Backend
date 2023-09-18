//package com.foodway.api.service;
//
//import com.foodway.api.record.RequestUserLogin;
//import com.foodway.api.model.User;
//import com.foodway.api.record.RequestUserData;
//import com.foodway.api.repository.UserRepository;
//import org.hibernate.validator.constraints.UUID;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    public ResponseEntity<User> getUsuario(UUID id) {
//        Optional<User> usuario = userRepository.findById(id);
//        if (usuario.isPresent()) {
//            return ResponseEntity.status(200).body(usuario.get());
//        }
//        return ResponseEntity.status(404).build();
//    }
//
//    public ResponseEntity<User> cadastrar(RequestUserData data) {
//        User user = new User(data);
//        return ResponseEntity.status(201).body(userRepository.save(user));
//    }
//
//    public ResponseEntity<User> loginUser(RequestUserLogin data) {
//        Optional<User> usuario = userRepository.findByEmailAndPassword(data.email(), data.senha());
//        if (usuario.isPresent()) {
//            // Aqui você pode criar um ResponseEntity com o usuário encontrado
//            return ResponseEntity.ok(usuario.get());
//        } else {
//            // Aqui você pode retornar um ResponseEntity com um status de erro (por exemplo, 404 Not Found)
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    public ResponseEntity<User> deleteUsuario(UUID id) {
//        Optional<User> usuario = userRepository.findById(id);
//        if (usuario.isPresent()) {
//            userRepository.delete(usuario.get());
//            return ResponseEntity.status(204).build();
//        }
//        return ResponseEntity.status(404).build();
//    }
//
//    public ResponseEntity<List<User>> getUsuarios() {
//        List<User> users = userRepository.findAll();
//        if (users.isEmpty()) {
//            return ResponseEntity.status(204).build();
//        }
//        return ResponseEntity.status(200).body(users);
//    }
//
//    public ResponseEntity<User> putUsuario(UUID id, @NotNull User user) {
//        Optional<User> usuarioOptional = userRepository.findById(id);
//        if (usuarioOptional.isPresent()) {
//            user.atualizar(usuarioOptional.get());
//            return ResponseEntity.status(200).body(userRepository.save(user));
//        }
//        return ResponseEntity.status(404).build();
//    }
//}
