package com.foodway.api.config.security;

import com.foodway.api.service.user.authentication.AuthenticationService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    private final AuthenticationService userAuthenticationService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProvider(AuthenticationService userAuthenticationService, PasswordEncoder passwordEncoder) {
        this.userAuthenticationService = userAuthenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();


        UserDetails userDetails = this.userAuthenticationService.loadUserByUsername(username);
        if(this.passwordEncoder.matches(password, userDetails.getPassword())){
            return  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        }else{
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
