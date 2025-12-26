package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Swagger endpoints
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()

                // AUTH endpoints
                .requestMatchers("/auth/**").permitAll()

                // 👉 ALLOW ALL METHODS FOR API (IMPORTANT)
                .requestMatchers(
                        "/courses/**",
                        "/lessons/**",
                        "/progress/**",
                        "/recommendations/**"
                ).permitAll()

                // Everything else
                .anyRequest().authenticated()
            )

            // For testing (OK for college project)
            .httpBasic(httpBasic -> {});

        return http.build();
    }
}
