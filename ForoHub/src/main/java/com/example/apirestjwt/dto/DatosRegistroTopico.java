  package com.example.apirestjwt.dto;

  import jakarta.validation.constraints.NotBlank;
  import jakarta.validation.constraints.NotNull;

  public record DatosRegistroTopico(
      @NotBlank
      String titulo,
      @NotBlank
      String mensaje,
      @NotNull
      Long idAutor,
      @NotBlank // Asegúrate de que nombreCurso también es obligatorio si lo es en la BD
      String nombreCurso
  ) {
}