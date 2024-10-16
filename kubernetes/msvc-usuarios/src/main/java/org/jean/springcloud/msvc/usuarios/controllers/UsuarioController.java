package org.jean.springcloud.msvc.usuarios.controllers;

import feign.Response;
import jakarta.validation.Valid;
import org.jean.springcloud.msvc.usuarios.mappers.UsuarioMapper;
import org.jean.springcloud.msvc.usuarios.models.entity.Usuario;
import org.jean.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario user, BindingResult result){

        if(result.hasErrors()){
            return getMapResponseEntity(result);
        }
        if(!user.getEmail().isEmpty() && usuarioService.porEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","Ya existe un Usuario con ese Email!"));
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario user,BindingResult result,@PathVariable Long id) {

        if(result.hasErrors()){
            return getMapResponseEntity(result);
        }

        Optional<Usuario> userActualizar = usuarioService.porId(id);
        if (userActualizar.isPresent()) {
            Usuario userDb = userActualizar.get();
            if(!user.getEmail().equalsIgnoreCase(userDb.getEmail()) && usuarioService.porEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","Ya existe un Usuario con ese Email!"));
            }

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

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerListaUsuariosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }


    private static ResponseEntity<Map<String, String>> getMapResponseEntity(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errores.put(error.getField(),"El campo "+error.getField()+" "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
