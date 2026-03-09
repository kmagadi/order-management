package com.training_spring_cloud.api_gateway.filter;

import com.training_spring_cloud.api_gateway.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayFilter apply() {
        return jwtAuthenticationFilter;
    }
}