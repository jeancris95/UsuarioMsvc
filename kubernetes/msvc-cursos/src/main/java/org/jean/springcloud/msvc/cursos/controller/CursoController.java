package org.jean.springcloud.msvc.cursos.controller;

import feign.FeignException;
import feign.Response;
import jakarta.validation.Valid;
import org.jean.springcloud.msvc.cursos.DTO.UsuarioDTO;
import org.jean.springcloud.msvc.cursos.entity.Curso;
import org.jean.springcloud.msvc.cursos.mappers.CursoMapper;
import org.jean.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {
    @Autowired
    CursoService cursoService;

    @Autowired
    CursoMapper cursoMapper;

    /*
     *Method getAll Curses
     */
    @GetMapping
    public ResponseEntity<List<Curso>> listaCurso(){
        List<Curso> listaCursos=cursoService.listarCurso();
        return ResponseEntity.ok(listaCursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurso(@PathVariable Long id){
        Optional<Curso> curso=cursoService.obtenerCurso(id);
        if(curso.isPresent()){
            return ResponseEntity.ok(curso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearCurso(@Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){
            return getMapResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>editarCurso(@Valid  @RequestBody Curso curso, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return getMapResponseEntity(result);
        }

        Optional<Curso> cursoActual=cursoService.obtenerCurso(id);
        if(cursoActual.isPresent()){
            Curso cursoDb=cursoActual.get();
            cursoMapper.actualizarCurso(curso,cursoDb);
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable Long id ){
        Optional<Curso> curso=cursoService.obtenerCurso(id);
        if(curso.isPresent()){
            cursoService.eliminarCurso(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody UsuarioDTO usuario, @PathVariable Long cursoId){
        Optional<UsuarioDTO> o ;
        try{
           o = cursoService.asignarUsuario(usuario,cursoId);
            if(o.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.
                    singletonMap("mensaje","No existe el usuario por id o error en la comunicacion "
                            +e.getMessage()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuario, @PathVariable Long cursoId){
        Optional<UsuarioDTO> o ;
        try{
            o = cursoService.crearUsuario(usuario,cursoId);
            if(o.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.
                    singletonMap("mensaje","No se pudo crear el usuario o error en la comunicacion "
                            +e.getMessage()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody UsuarioDTO usuario, @PathVariable Long cursoId){
        Optional<UsuarioDTO> o ;
        try{
            o = cursoService.eliminarUsuario(usuario,cursoId);
            if(o.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(o.get());
            }
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.
                    singletonMap("mensaje","No existe el usuario por id o error en la comunicacion "
                            +e.getMessage()));
        }
        return ResponseEntity.notFound().build();
    }


    private static ResponseEntity<Map<String, String>> getMapResponseEntity(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errores.put(error.getField(),"El campo "+error.getField()+" "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
