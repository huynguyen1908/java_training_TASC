package com.example.Gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class BeanConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("USER-SERVICE",r -> r.path("/api/user/**", "/api/auth/**")
                        .uri("lb://USER-SERVICE/"))
                .route("PRODUCT-SERVICE", r -> r.path("/api/product/**", "/api/cart/**", "/api/category/**")
                        .uri("lb://PRODUCT-SERVICE/"))
                .route("ORDER-SERVICE", r -> r.path("/api/orders/**")
                        .uri("lb://ORDER-SERVICE/"))
                .route("INVENTORY-SERVICE", r -> r.path("api/inventory/**")
                        .uri("lb://INVENTORY-SERVICE/"))
                .route("PAYMENT-SERVICE", r -> r.path("api/payment/**")
                        .uri("lb://PAYMENT-SERVICE/"))
                .route("NOTIFICATION-SERVICE", r -> r.path("/api/notification/**")
                        .uri("lb://NOTIFICATION-SERVICE"))
                .build();
    }
//
    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "http://localhost:4200";
    private static final String MAX_AGE = "3600";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            ServerHttpResponse response = ctx.getResponse();

            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "authorization, content-type, x-requested-with");
            headers.add("Access-Control-Allow-Credentials", "true");
            headers.add("Access-Control-Max-Age", "3600");

            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }

            return chain.filter(ctx);
        };
    }

}
