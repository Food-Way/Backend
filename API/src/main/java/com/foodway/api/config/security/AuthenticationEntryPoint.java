package com.foodway.api.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint
{
//    private SecutiryConfiguration securityConfiguration;
//
//    public AuthenticationEntryPoint() {
//    }

    //    public AuthenticationEntryPoint(SecutiryConfiguration securityConfiguration) {
//        this.securityConfiguration = securityConfiguration;
//    }



//    @Autowired
//    public void setSecurityConfiguration(SecutiryConfiguration securityConfiguration) {
//        this.securityConfiguration = securityConfiguration;
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(authException.getClass().equals(BadCredentialsException.class)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }


}
