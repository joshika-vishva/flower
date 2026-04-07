package com.flowershop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // Permit API requests without authentication for now
                .requestMatchers("/h2-console/**").permitAll() // Permit H2 console access
                .anyRequest().permitAll()

            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Required for H2 console
        
        return http.build();
    }
}
