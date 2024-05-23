package com.foodway.api.service;

import com.foodway.api.config.security.jwt.ManagerToken;
import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.model.Enums.ETypeUser;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.User;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.UserRepository;
import com.foodway.api.service.user.authentication.dto.UserCreateDto;
import com.foodway.api.service.user.authentication.dto.UserLoginDto;
import com.foodway.api.service.user.authentication.dto.UserMapper;
import com.foodway.api.service.user.authentication.dto.UserTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    ManagerToken managerToken;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EstablishmentRepository establishmentRepository;


    public void create(UserCreateDto userCreateDto) {

        final User newUser = UserMapper.of(userCreateDto);
        String passwordEncrypted = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passwordEncrypted);
        this.userRepository.save(newUser);
    }

    public UserTokenDto authenticate(UserLoginDto userLoginDto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new ResponseStatusException(404, "Email não cadastrado", null));
        final Authentication authentication = this.authenticationManager.authenticate(credentials);
        String establishmentName = null;
        String culinary = null;

        if(user.getTypeUser() == ETypeUser.ESTABLISHMENT) {
            Establishment establishment = establishmentRepository.findById(user.getIdUser()).orElseThrow(() -> new EstablishmentNotFoundException("Estabelecimento não encontrado"));
            establishmentName = establishment.getEstablishmentName();
            culinary = establishment.getCulinary().get(0).getName();
        }


        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = managerToken.generateToken(authentication);
        return UserMapper.of(user, token, establishmentName, culinary);

    }

}


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
