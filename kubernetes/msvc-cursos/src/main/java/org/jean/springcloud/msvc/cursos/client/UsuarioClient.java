package org.jean.springcloud.msvc.cursos.client;

import org.jean.springcloud.msvc.cursos.DTO.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

//la url se quitara cuando integremos spring cloud
@FeignClient(name = "msvc-usuarios", url = "http://localhost:8001")
public interface UsuarioClient {

    @GetMapping("/{id}")
     UsuarioDTO obtenerUsuarioPorId(@PathVariable Long id);

    @PostMapping("/")
    UsuarioDTO crear(@RequestBody UsuarioDTO usuario);

}
