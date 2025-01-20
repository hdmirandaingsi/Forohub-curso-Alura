package com.example.apirestjwt.controller;

import com.example.apirestjwt.dto.DatosActualizarTopico;
import com.example.apirestjwt.dto.DatosListadoTopico;
import com.example.apirestjwt.dto.DatosRegistroTopico;
import com.example.apirestjwt.model.Topico; 
import com.example.apirestjwt.security.TopicoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosListadoTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        DatosListadoTopico datosListadoTopico = topicoService.registrarTopico(datosRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosListadoTopico.id()).toUri();
        return ResponseEntity.created(url).body(datosListadoTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        Page<DatosListadoTopico> listadoTopicos = topicoService.obtenerTodosLosTopicos(paginacion);
        return ResponseEntity.ok(listadoTopicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> retornarTopico(@PathVariable Long id) {
        try {
            Topico topico = topicoService.obtenerTopicoPorId(id);
            return ResponseEntity.ok(new DatosListadoTopico(topico));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

     @PutMapping("/{id}") // Usar @PathVariable para capturar el ID de la URL
@Transactional
public ResponseEntity<DatosListadoTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
    // Asegúrate de que el ID del cuerpo coincida con el ID de la URL
    if (!id.equals(datosActualizarTopico.id())) {
        return ResponseEntity.badRequest().build(); // O lanzar una excepción
    }
    DatosListadoTopico datosListadoTopico = topicoService.actualizarTopico(datosActualizarTopico);
    return ResponseEntity.ok(datosListadoTopico);
}

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}