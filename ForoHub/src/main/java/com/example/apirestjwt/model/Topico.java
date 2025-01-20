package com.example.apirestjwt.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// DB generara ID auto incremental unico
    private Long id;

    private String titulo;
    private String mensaje;

    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    private String nombreCurso;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public void actualizarDatos(String titulo, String mensaje, String nombreCurso) {
    if (titulo != null) {
        this.titulo = titulo;
    }
    if (mensaje != null) {
        this.mensaje = mensaje;
    }
    if (nombreCurso != null) {
        this.nombreCurso = nombreCurso;
    }
}
}