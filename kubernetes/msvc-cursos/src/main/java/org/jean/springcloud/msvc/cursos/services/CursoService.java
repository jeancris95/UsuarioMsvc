package org.jean.springcloud.msvc.cursos.services;

import org.jean.springcloud.msvc.cursos.entity.Curso;
import org.jean.springcloud.msvc.cursos.DTO.UsuarioDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface CursoService  {
    List<Curso> listarCurso();
    Optional<Curso> obtenerCurso(Long id);
    Curso guardar(Curso curso);
    void eliminarCurso(Long id);
    Optional<Curso>porIdConUsuarios(Long id);
    void eliminarCursoUsuarioPorId(Long id);
    //esto se obtiene de otro servicio

    /*
     * Al curso con este id que se recibe por parametro le vamos a asignar el usuario q
     * se le mande por parametro
     */
    Optional<UsuarioDTO>asignarUsuario(UsuarioDTO usuario, Long cursoId);

    /*
     * Crea un usuario y le asigna ese curso Id
     */

    Optional<UsuarioDTO>crearUsuario(UsuarioDTO usuario , Long cursoId);

    /*
     * Desasignamos el curso del usuario
     */

    Optional<UsuarioDTO>eliminarUsuario(UsuarioDTO usuario , Long cursoId);

}
