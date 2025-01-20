package com.example.apirestjwt.dto;

import com.example.apirestjwt.model.Topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Long idAutor, // Cambiado a idAutor
        String nombreCurso
) {
    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getId(), topico.getNombreCurso());
    }
}