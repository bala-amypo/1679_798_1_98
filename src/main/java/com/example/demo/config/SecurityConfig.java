package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // disable CSRF for simplicity
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/auth/**").permitAll()  // allow login/register endpoints
                .anyRequest().authenticated()            // all other endpoints require auth
            )
            .httpBasic(); // use basic HTTP auth instead of JWT for now

        return http.build();
    }
}
