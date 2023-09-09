package com.foodway.api.service;

import com.foodway.api.controller.RequestUserLogin;
import com.foodway.api.model.Usuario;
import com.foodway.api.record.RequestUserData;
import com.foodway.api.repository.UsuarioRepository;
import org.hibernate.validator.constraints.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    public ResponseEntity<Usuario> getUsuario(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.status(200).body(usuario.get());
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> cadastrar(RequestUserData data) {
        Usuario usuario = new Usuario(data);
        return ResponseEntity.status(201).body(usuarioRepository.save(usuario));
    }

    public ResponseEntity<Usuario> loginUser(RequestUserLogin data) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndPassword(data.email(), data.senha());
        if (usuario.isPresent()) {
            // Aqui você pode criar um ResponseEntity com o usuário encontrado
            return ResponseEntity.ok(usuario.get());
        } else {
            // Aqui você pode retornar um ResponseEntity com um status de erro (por exemplo, 404 Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Usuario> deleteUsuario(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    public ResponseEntity<Usuario> putUsuario(UUID id,@NotNull Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            usuario.atualizar(usuarioOptional.get());
            return ResponseEntity.status(200).body(usuarioRepository.save(usuario));
        }
        return ResponseEntity.status(404).build();
    }
}
