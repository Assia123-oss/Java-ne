package com.java.ne_starter.controllers;

import com.java.ne_starter.dtos.auth.LoginDto;
import com.java.ne_starter.dtos.auth.AuthResponse;
import com.java.ne_starter.dtos.auth.RegisterDto;
import com.java.ne_starter.dtos.response.ApiResponse;
import com.java.ne_starter.services.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDto loginDTO) {
        return authenticationService.login(loginDTO);
    }
}