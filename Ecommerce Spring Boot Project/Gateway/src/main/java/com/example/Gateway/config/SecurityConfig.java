//package com.example.Gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/users/**").authenticated() // Chỉ cho phép các yêu cầu có xác thực
//                        .anyRequest().permitAll() // Các yêu cầu khác được phép mà không cần xác thực
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Cấu hình xác thực JWT
//        return http.build();
//    }
//}
