package com.mrjob.entities.Servicios;

import com.mrjob.entities.usuarios.CatUsuario;
import javax.persistence.*;

@Entity
public class UsuarioServicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private CatUsuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    private CatServicios servicio;

    // constructor vacio
    public UsuarioServicios() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(CatUsuario usuario) {
        this.usuario = usuario;
    }

    public CatServicios getServicio() {
        return servicio;
    }

    public void setServicio(CatServicios servicio) {
        this.servicio = servicio;
    }
}


/** Esta es la clase que tiene las relaciones -> ManyToOne
 *
 * 1.- Se agrego codigo en la clase UsuarioEntity y en RolEntity
 *
 * 2.- @ManyToOne (fetch = FetchType.EAGER) de la linea #12 del usuario: MUCHOS roles le van a poder pertenecer a UN usuario <-- PARA ESTO EN ESTA CLASE EN LA CLASE
 * DE UsuarioEntity hay es donde tambien tiene la anotaciones de uno a muchos es la CONTRAPARTE
 *
 * 3.- @ManyToOne(fetch = FetchType.EAGER): muchos usuarios van a poder pertenecer un solo ROL
 *
 */