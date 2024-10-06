package org.jean.springcloud.msvc.usuarios.mappers;

import org.jean.springcloud.msvc.usuarios.models.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    void actualizarUsuario(Usuario user, @MappingTarget Usuario userDb);
}
