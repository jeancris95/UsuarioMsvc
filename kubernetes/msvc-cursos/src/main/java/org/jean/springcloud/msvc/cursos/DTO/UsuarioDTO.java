package org.jean.springcloud.msvc.cursos.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsuarioDTO {
    private long id;
    private String nombre;
    private String email;
    @JsonIgnore
    private String password;
    // Constructor vacío
    public UsuarioDTO() {
    }

    // Constructor completo
    public UsuarioDTO(long id, String nombre, String email,String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
