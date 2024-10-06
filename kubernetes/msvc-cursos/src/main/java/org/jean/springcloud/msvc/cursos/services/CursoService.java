package org.jean.springcloud.msvc.cursos.services;

import org.jean.springcloud.msvc.cursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService  {
    List<Curso> listarCurso();
    Optional<Curso> obtenerCurso(Long id);
    Curso guardar(Curso curso);
    void eliminarCurso(Long id);
}
