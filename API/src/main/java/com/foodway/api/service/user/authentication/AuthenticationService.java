package com.foodway.api.service.user.authentication;

import com.foodway.api.model.User;
import com.foodway.api.repository.UserRepository;
import com.foodway.api.service.user.authentication.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw  new UsernameNotFoundException(String.format("User %s not found", email));
        }
        User user = userOptional.get();
        String passwordData = user.getPassword();

        // Verifique a senha usando o BCryptPasswordEncoder

        boolean wrongPassword = passwordEncoder.matches("password123", passwordData);

        if (!wrongPassword) {
            throw new BadCredentialsException("Senha incorreta");
        }

        return new UserDetailsDto(user);

    }
}
