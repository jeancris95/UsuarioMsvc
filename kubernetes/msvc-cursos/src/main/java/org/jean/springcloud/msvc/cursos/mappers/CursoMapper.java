package org.jean.springcloud.msvc.cursos.mappers;

import org.jean.springcloud.msvc.cursos.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(target = "id", ignore = true)
    void actualizarCurso(Curso curso, @MappingTarget Curso cursoDb);

}
