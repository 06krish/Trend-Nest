package com.trendnest.trendnest_backend.security;

import com.trendnest.trendnest_backend.entity.User;
import com.trendnest.trendnest_backend.repository.UserRepository;
import com.trendnest.trendnest_backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        // No JWT provided
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Extract JWT
        String token = authHeader.substring(7);
        try {
            // Extract email from JWT
            String email = jwtService.extractEmail(token);

            // Check if user is not already authenticated
            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Find user in database
                User user = userRepository.findByEmail(email)
                        .orElse(null);

                // Validate JWT
                if (user != null && jwtService.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    null
                            );
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );
                    // Mark user as authenticated
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception e) {
            // Invalid/expired JWT
            // Continue without authentication
        }
        filterChain.doFilter(request, response);
    }
}