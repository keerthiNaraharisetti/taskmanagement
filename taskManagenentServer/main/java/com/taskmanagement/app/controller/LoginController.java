package com.taskmanagement.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.taskmanagement.app.dto.AuthTokenDTO;
import com.taskmanagement.app.request.LoginRequest;
import com.taskmanagement.app.service.JWTTokenService;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenService jwtTokenService;

    @PostMapping
    public AuthTokenDTO login(@RequestBody LoginRequest loginRequest) throws AuthenticationException{
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenService.generateToken(authentication);
        return new AuthTokenDTO(token);
    }
}
