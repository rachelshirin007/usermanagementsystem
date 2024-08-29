package com.ashville.usermanagementsystem.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ashville.usermanagementsystem.services.JWTUtils;
import com.ashville.usermanagementsystem.services.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter{
    
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if(authHeader == null || authHeader.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        // Check if the token is blacklisted
        if (tokenBlacklistService.isTokenBlacklisted(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        userEmail = jwtUtils.getUsernameFromToken(jwtToken);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            if(jwtUtils.isTokenValid(jwtToken, userDetails)){

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);

            }
        }
        filterChain.doFilter(request, response);
    }
}
