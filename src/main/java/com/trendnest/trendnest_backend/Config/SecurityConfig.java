package com.trendnest.trendnest_backend.Config;

import com.trendnest.trendnest_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/product/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.POST,"/api/product/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/product/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/product/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/product/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/categories", "/api/categories/**")
                        .authenticated()

                        // category Post - ADMIN
                        .requestMatchers(HttpMethod.POST,"/api/categories")
                        .hasRole("ADMIN")

                        // Category UPDATE - ADMIN
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**")
                        .hasRole("ADMIN")

                        // Category PATCH - ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/api/categories/**")
                        .hasRole("ADMIN")

                        // Category DELETE - ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}