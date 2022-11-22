package com.mrjob.entities.Roles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cat_roles")
public class CatRol {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    @Column(name = "nombre_rol")
    private String nombreRol;
    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<UsuarioRol> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }

    // constructor vacio
    public CatRol() {

    }
}

/**
 * 1.- @OneToMany: Un rol de editor le puede pertencer a muchos usuarios
 * 2.- cascade = CascadeType.ALL; Cuando eliminemos este rol tambien elimine el rol que estaba relacionado a los demas usuarios las k estan relacionadas
 *
 * 3.- fetch = FetchType.LAZY; cuando vamos a usar una busqueda se debe de indicar como se va arealizar la busqueda
 *
 * 4.- mappedBy = "rol": la propietaria va ser rol se indica en la clase -> UsuarioRolEntity
 *
 */
