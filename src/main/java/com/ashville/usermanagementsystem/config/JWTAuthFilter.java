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


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;
        System.out.println("=============in doFilterInterval===============");

        if(authHeader == null || authHeader.isBlank()){
            filterChain.doFilter(request, response);
            System.out.println("=============request in JWTAuthFilter==============="+request);
            System.out.println("=============response in JWTAuthFilter==============="+response);


            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.getUsernameFromToken(jwtToken);
        System.out.println("=============userEmail===============" + userEmail);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            if(jwtUtils.isTokenValid(jwtToken, userDetails)){

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("=============token==============="+ token);

                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
                System.out.println("=============securityContext==============="+securityContext);

            }
        }
        filterChain.doFilter(request, response);
    }
}
