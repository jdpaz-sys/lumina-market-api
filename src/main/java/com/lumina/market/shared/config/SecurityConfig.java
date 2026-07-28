package com.lumina.market.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (necesario para APIs REST)
                .csrf(csrf -> csrf.disable())
                // Configurar autorización de endpoints
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso a todos los endpoints de la API
                        .requestMatchers("/api/**").permitAll()
                        // Permitir acceso a Swagger/OpenAPI (lo agregaremos después)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Permitir acceso a Actuator
                        .requestMatchers("/actuator/**").permitAll()
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                // Deshabilitar el formulario de login por defecto
                .formLogin(form -> form.disable())
                // Deshabilitar logout por defecto
                .logout(logout -> logout.disable());

        return http.build();
    }
}