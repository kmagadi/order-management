package com.training_spring_cloud.api_gateway.config;

import com.training_spring_cloud.api_gateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        return builder.routes()

                // Auth service (no authentication required)
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))

                // Order service (JWT required)
                .route("order-service", r -> r
                        .path("/orders/**")
                        .filters(f -> f.filter(authenticationFilter.apply()))
                        .uri("lb://ORDER-SERVICE"))

                // Analytics service (JWT required)
                .route("analytics-service", r -> r
                        .path("/analytics/**")
                        .filters(f -> f.filter(authenticationFilter.apply()))
                        .uri("lb://ANALYTICS-SERVICE"))

                .build();
    }
}