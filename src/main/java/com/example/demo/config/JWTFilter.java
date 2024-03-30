package com.example.demo.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.authentication.UserDetailsServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableMethodSecurity(jsr250Enabled = true)
public class JWTFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    private final UserDetailsServiceImp userDetailsServiceImp;

    public JWTFilter(JwtUtility jwtUtility, UserDetailsServiceImp userDetailsServiceImp) {
        this.jwtUtility = jwtUtility;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt;
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);//Chuyển tiếp request và response cho các filter chain khác
            return;
        }
        jwt = authHeader.substring(7);
        if (jwtUtility.validateToken(jwt)) {
            String email = jwtUtility.extractClaimsFromJwtToken(jwt).getSubject();
            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
