package com.foodway.api.controller;


import com.foodway.api.model.Usuario;
import com.foodway.api.record.RequestUserData;
import com.foodway.api.service.UsuarioService;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/id")
    public ResponseEntity<Usuario> getUsuario(@PathVariable UUID id) {
        return usuarioService.getUsuario(id);
    }

   @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Validated RequestUserData data) {
        return usuarioService.cadastrar(data);
    }
    @GetMapping
    public ResponseEntity<Usuario> login(@RequestBody @Validated RequestUserLogin data) {
        return usuarioService.loginUser(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable UUID id) {
        return usuarioService.deleteUsuario(id);
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
