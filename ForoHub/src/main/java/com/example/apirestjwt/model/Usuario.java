package com.example.apirestjwt.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections; 
@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter // el metodo getId() lo genera automatico por las DEPENDENCIAS
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String clave;
    private String nombre;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Retorna una lista vacía de roles
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}