package com.foodway.api.repository;

import com.foodway.api.model.Usuario;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

   public Optional<Usuario> findByEmailAndPassword(String email, String senha);

}
