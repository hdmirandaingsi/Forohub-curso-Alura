package com.example.apirestjwt.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo, // Ahora es opcional
        String mensaje, // Ahora es opcional
        String nombreCurso // Ahora es opcional
) {
}