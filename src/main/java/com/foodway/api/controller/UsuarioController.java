package com.foodway.api.controller;


import com.foodway.api.model.Usuario;
import com.foodway.api.record.RequestUserData;
import com.foodway.api.service.UsuarioService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios-teste") ResponseEntity<List<Usuario>> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping("/id")
    public ResponseEntity<Usuario> getUsuario(@PathVariable UUID id) {
        return usuarioService.getUsuario(id);
    }

    @GetMapping
    public ResponseEntity<Usuario> login(@RequestBody @Validated RequestUserLogin data) {
        return usuarioService.loginUser(data);
    }

   @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Validated RequestUserData data) {
        return usuarioService.cadastrar(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable UUID id) {
        return usuarioService.deleteUsuario(id);
    }

    @PutMapping
    public ResponseEntity<Usuario> put(@PathVariable UUID id, @RequestBody Usuario usuario) {
        return usuarioService.putUsuario(id, usuario);
    }
    /*
        {
            "nome": "nome",
            "sobrenome": "sobrenome",
            "email": "email",
            "senha": "senha"
        }
     */


}
