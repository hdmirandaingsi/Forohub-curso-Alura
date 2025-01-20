package com.example.apirestjwt.repository;

import com.example.apirestjwt.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
    Optional<Usuario> findById(Long id); // Método para buscar por ID
}