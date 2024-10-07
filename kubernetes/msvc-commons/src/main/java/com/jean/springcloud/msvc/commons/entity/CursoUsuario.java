package com.jean.springcloud.msvc.commons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "curso_id")
    private Long cursoId;

    public CursoUsuario() {}

    public CursoUsuario(Long cursoId, Long usuarioId) {
        this.cursoId = cursoId;
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof CursoUsuario)){
            return false;
        }
        CursoUsuario o = (CursoUsuario) obj;
        return this.usuarioId !=null && this.usuarioId.equals(o.usuarioId);
    }
}
