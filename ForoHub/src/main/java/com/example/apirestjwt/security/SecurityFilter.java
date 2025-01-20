package com.example.apirestjwt.security;

import com.example.apirestjwt.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
/*
esta CLASE intercepta los RequestHTTP antes del controlador
*/


@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Autowired
    private AutenticacionService autenticacionService;
    
    @Autowired
    private GeneracionGestionToken tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // Obtener el token del header Authorization
    var authHeader = request.getHeader("Authorization");
    System.out.println("AUTH HEADER: " + authHeader);
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        var token = authHeader.replace("Bearer ", "");

        // Usar AutenticacionService para cargar los detalles del usuario
        String subject = tokenService.getSubject(token);
        System.out.println("SUBJECT: " + subject);
        if (subject != null) {
            UserDetails userDetails = autenticacionService.loadUserByUsername(subject);
            System.out.println("USER DETAILS: " + userDetails);

            if (userDetails != null) {
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                System.out.println("AUTHENTICATION: " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("AUTENTICACIÓN ESTABLECIDA: " + SecurityContextHolder.getContext().getAuthentication());
            }
        }
    }

    filterChain.doFilter(request, response);
}
}