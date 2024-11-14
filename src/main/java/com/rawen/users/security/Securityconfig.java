package com.rawen.users.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationManager authMgr;

    // Configuration de l'AuthenticationManager avec le UserDetailsService et BCryptPasswordEncoder
    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             BCryptPasswordEncoder bCryptPasswordEncoder,
                                             UserDetailsService userDetailsService) 
                                             throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(bCryptPasswordEncoder)
                   .and()
                   .build();
    }

    // Configuration de la sécurité HTTP et des filtres
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Désactivation de CSRF car l'application est stateless (utilisation de JWT)
            .csrf(csrf -> csrf.disable())

            // Configuration de CORS pour autoriser les requêtes depuis http://localhost:4200
            .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    cors.setAllowedMethods(Collections.singletonList("*"));
                    cors.setAllowedHeaders(Collections.singletonList("*"));
                    cors.setExposedHeaders(Collections.singletonList("Authorization"));
                    return cors;
                }
            }))

            // Configuration des autorisations pour les endpoints
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/login", "/users/register", "/verifyEmail/**").permitAll()  // Autorise l'accès sans authentification
                .requestMatchers("/all").hasAuthority("ADMIN")  // Restreint cet endpoint aux utilisateurs avec le rôle ADMIN
                .anyRequest().authenticated())  // Exige l'authentification pour toutes les autres requêtes

            // Ajout des filtres JWT pour l'authentification et l'autorisation
            .addFilterBefore(new JWTAuthenticationFilter(authMgr), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}