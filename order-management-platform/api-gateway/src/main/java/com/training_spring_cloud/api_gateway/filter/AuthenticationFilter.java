package com.training_spring_cloud.api_gateway.filter;

import com.training_spring_cloud.api_gateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            try {

                String path = exchange.getRequest().getURI().getPath();
                System.out.println("Request path: " + path);

                if (path.startsWith("/auth")) {
                    return chain.filter(exchange);
                }

                var headers = exchange.getRequest().getHeaders();
                var authHeaders = headers.getOrEmpty("Authorization");

                if (authHeaders.isEmpty()) {
                    System.out.println("Authorization header missing");
                    return unauthorized(exchange);
                }

                String authHeader = authHeaders.get(0);

                if (!authHeader.startsWith("Bearer ")) {
                    System.out.println("Invalid Authorization header");
                    return unauthorized(exchange);
                }

                String token = authHeader.substring(7);

                System.out.println("Token received: " + token);

                var claims = jwtUtil.validateToken(token);

                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                System.out.println("Token valid. User: " + username + " Role: " + role);

                var mutatedRequest = exchange.getRequest()
                        .mutate()
                        .header("X-User", username)
                        .header("X-Role", role)
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (Exception e) {

                System.out.println("JWT validation failed: " + e.getMessage());
                return unauthorized(exchange);
            }
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {}
}