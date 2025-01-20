package com.example.apirestjwt.security;

import com.example.apirestjwt.dto.DatosActualizarTopico;
import com.example.apirestjwt.dto.DatosListadoTopico;
import com.example.apirestjwt.dto.DatosRegistroTopico;
import com.example.apirestjwt.model.Topico;
import com.example.apirestjwt.model.Usuario;
import com.example.apirestjwt.repository.TopicoRepository;
import com.example.apirestjwt.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
      public DatosListadoTopico registrarTopico(DatosRegistroTopico datosRegistroTopico) {
          Usuario autor = usuarioRepository.findById(datosRegistroTopico.idAutor())
                  .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + datosRegistroTopico.idAutor()));

          Topico topico = new Topico();
          topico.setTitulo(datosRegistroTopico.titulo());
          topico.setMensaje(datosRegistroTopico.mensaje());
          topico.setAutor(autor);
          topico.setNombreCurso(datosRegistroTopico.nombreCurso()); // Asegúrate de esta línea

          topico = topicoRepository.save(topico);

          return new DatosListadoTopico(topico);
      }

    public Page<DatosListadoTopico> obtenerTodosLosTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
    }

    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));
    }

     @Transactional
    public DatosListadoTopico actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.findById(datosActualizarTopico.id())
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + datosActualizarTopico.id()));

        topico.actualizarDatos(datosActualizarTopico.titulo(), datosActualizarTopico.mensaje(), datosActualizarTopico.nombreCurso());

        return new DatosListadoTopico(topico);
    }

    @Transactional
    public void eliminarTopico(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));

        topicoRepository.delete(topico);
    }
}