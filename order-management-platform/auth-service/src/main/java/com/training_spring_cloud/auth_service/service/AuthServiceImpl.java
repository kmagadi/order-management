package com.training_spring_cloud.auth_service.service;

import com.training_spring_cloud.auth_service.dto.LoginRequest;
import com.training_spring_cloud.auth_service.dto.LoginResponse;
import com.training_spring_cloud.auth_service.model.Role;
import com.training_spring_cloud.auth_service.model.User;
import com.training_spring_cloud.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private Map<String, User> users;

    public AuthServiceImpl(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;

        this.users = Map.of(
                "admin", new User("admin", passwordEncoder.encode("password"), Role.ADMIN),
                "trader", new User("trader", passwordEncoder.encode("password"), Role.TRADER),
                "viewer", new User("viewer", passwordEncoder.encode("password"), Role.VIEWER)
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = users.get(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .role(user.getRole().name())
                .build();
    }
}