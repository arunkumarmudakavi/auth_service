package com.example.assignment.config;

import com.example.assignment.service.CustomUserDetailsService;
import com.example.assignment.utils.JwtToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthFilter(
            JwtToken jwtToken,
            CustomUserDetailsService customUserDetailsService
    ) {

        this.jwtToken = jwtToken;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getServletPath();

        // skip auth routes
        if (requestPath.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = parseJwt(request);

        // No token present return
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String email = jwtToken.extractEmail(jwt);

            if (email != null && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                boolean isValid = jwtToken.validateToken(jwt, userDetails.getUsername());

                if (isValid) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("Invalid JWT Token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        return jwtToken.getJwtFromHeader(request);
    }
}
