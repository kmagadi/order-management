package com.training_spring_cloud.auth_service.controller;

import com.training_spring_cloud.auth_service.dto.LoginRequest;
import com.training_spring_cloud.auth_service.dto.LoginResponse;
import com.training_spring_cloud.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }
}