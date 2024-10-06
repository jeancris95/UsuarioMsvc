package org.jean.springcloud.msvc.cursos.ServiceImpl;

import org.jean.springcloud.msvc.cursos.entity.Curso;
import org.jean.springcloud.msvc.cursos.repositories.CursoRepository;
import org.jean.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursosServiceImpl implements CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Override
    public List<Curso> listarCurso() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> obtenerCurso(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminarCurso(Long id) {
         cursoRepository.deleteById(id);
    }
}
