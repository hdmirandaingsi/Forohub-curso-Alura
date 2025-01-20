package com.example.apirestjwt.security;

import com.example.apirestjwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Asegúrate de que esta anotación esté presente
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar a: " + username);
        UserDetails user = usuarioRepository.findByLogin(username);
        if (user != null) {
            System.out.println("Usuario encontrado: " + user.getUsername());
            return user;
        } else {
            System.out.println("Usuario no encontrado: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
}