package com.mrjob.entities.Servicios;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cat_servicios")
public class CatServicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long id_servicio;
    @Column(name = "nombre_servicio")
    private String nombre_servicio;
    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "servicio")
    private Set<UsuarioServicios> usuarioServicios = new HashSet<>();

    public Long getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(Long id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<UsuarioServicios> getUsuarioServicios() {
        return usuarioServicios;
    }

    public void setUsuarioServicios(Set<UsuarioServicios> usuarioServicios) {
        this.usuarioServicios = usuarioServicios;
    }

    public CatServicios() {
    }
}
