package org.jean.springcloud.msvc.usuarios.controllers;

import feign.Response;
import org.jean.springcloud.msvc.usuarios.mappers.UsuarioMapper;
import org.jean.springcloud.msvc.usuarios.models.entity.Usuario;
import org.jean.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;
    /*
     *
     */
    @GetMapping
    public ResponseEntity<List<Usuario>>listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listar(); // Asumiendo que este método devuelve una lista de usuarios.
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> userOptional=usuarioService.porId(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
      return ResponseEntity.notFound().build();
    }
    /*201 cuando se crea el user ,(es el codigo de respuesta)
    *En el post podemos devolver un ResponseEntity o devolver el usuario
    *si devolvemos un responseEntity podemos devolver el status 201
    * si es created y en el body se enviaria el save del service
     */
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@RequestBody Usuario user){
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario user,@PathVariable Long id) {
        Optional<Usuario> userActualizar = usuarioService.porId(id);
        if (userActualizar.isPresent()) {
            Usuario userDb = userActualizar.get();
            // Usamos el mapper para copiar las propiedades
            usuarioMapper.actualizarUsuario(user, userDb);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(userDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> user = usuarioService.porId(id);
        if (user.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent()
                    .build();
        }
        return ResponseEntity.notFound().build();
    }
}
