package org.jean.springcloud.msvc.cursos.ServiceImpl;

import com.jean.springcloud.msvc.commons.entity.CursoUsuario;
import org.jean.springcloud.msvc.cursos.client.UsuarioClient;
import org.jean.springcloud.msvc.cursos.entity.Curso;
import org.jean.springcloud.msvc.cursos.DTO.UsuarioDTO;
import org.jean.springcloud.msvc.cursos.repositories.CursoRepository;
import org.jean.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursosServiceImpl implements CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioClient usuarioClient;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarCurso() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> obtenerCurso(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminarCurso(Long id) {
         cursoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = cursoRepository.findById(id);
        if(o.isPresent()){
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
               List<Long> listaId= curso.getCursoUsuarios().stream().map(
                       cu ->cu.getUsuarioId()
               ).collect(Collectors.toList());
               List<UsuarioDTO> usuarios=usuarioClient.obtenerListaUsuariosPorCurso(listaId);
               curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UsuarioDTO> asignarUsuario(UsuarioDTO usuario, Long cursoId) {
        //se busca el curso en la bbdd a traves del id del curso
        Optional<Curso> o=cursoRepository.findById(cursoId);
        if(o.isPresent()){
            //buscamos el usuario a traves del id que se recibe por parametro, esto
            //lo optiene desde el microservicio Usuario
            UsuarioDTO userMsvc = usuarioClient.obtenerUsuarioPorId(usuario.getId());
            //obtenemos la instancia del curso
            Curso curso= o.get();
            // se crea instancia de cursoUsuario
            CursoUsuario cursoUsuario= new CursoUsuario();
            cursoUsuario.setUsuarioId(userMsvc.getId());
             //   cursoUsuario.setCursoId(cursoId);
            //se agrega la lista de relaciones a curso
            curso.addCursoUsuario(cursoUsuario);
            // Guardar la relación en la base de datos
            cursoRepository.save(curso);
            //se devuelve el usuario
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UsuarioDTO> crearUsuario(UsuarioDTO usuario, Long cursoId) {
        //se busca el curso en la bbdd a traves del id del curso
        Optional<Curso> o=cursoRepository.findById(cursoId);
        if(o.isPresent()){
            UsuarioDTO userNuevoMsvc = usuarioClient.crear(usuario);
            //obtenemos la instancia del curso
            Curso curso= o.get();
            // se crea instancia de cursoUsuario
            CursoUsuario cursoUsuario= new CursoUsuario();
            cursoUsuario.setUsuarioId(userNuevoMsvc.getId());
            //   cursoUsuario.setCursoId(cursoId);
            //se agrega la lista de relaciones a curso
            curso.addCursoUsuario(cursoUsuario);
            // Guardar la relación en la base de datos
            cursoRepository.save(curso);
            //se devuelve el usuario
            return Optional.of(userNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UsuarioDTO> eliminarUsuario(UsuarioDTO usuario, Long cursoId) {
        Optional<Curso> o=cursoRepository.findById(cursoId);
        if(o.isPresent()){
            UsuarioDTO userMsvc = usuarioClient.obtenerUsuarioPorId(usuario.getId());
            //obtenemos la instancia del curso
            Curso curso= o.get();
            // se crea instancia de cursoUsuario
            CursoUsuario cursoUsuario= new CursoUsuario();
            cursoUsuario.setUsuarioId(userMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);

            cursoRepository.save(curso);
            //se devuelve el usuario
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }
}
