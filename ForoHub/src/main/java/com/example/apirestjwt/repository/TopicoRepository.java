package com.example.apirestjwt.repository;

import com.example.apirestjwt.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}