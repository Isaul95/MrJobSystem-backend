package com.mrjob.service;

import com.mrjob.dto.CatUsuarioDTO;
import com.mrjob.dto.Response;
import com.mrjob.entities.Roles.UsuarioRol;
import com.mrjob.entities.usuarios.CatUsuario;
import java.util.Set;

public interface UsuarioService {
/**
 * Este metodo guarda es un nuevo usuario y le vasmos a pasar un conjunto de usuarioRoles, un conjunto de roles que
 * se le va asignar al new usuario
 * CREAMOS UNA CLASE APARTE.! Cuando se trabaja con relaciones de uno - muchos por ambos lados es RECOMENDABLE
 * NORMALIZAR y crear una tabla intermedia llamada -> UsuarioRol que esta en las entities
 */
//    public CatUsuario guardarUsuario(CatUsuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;
    public CatUsuarioDTO guardarUsuario(CatUsuarioDTO usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

    public CatUsuario obtenerUsuario(String usuario);

    public CatUsuario actualizarCategoria(CatUsuario usuario);

    public void eliminarUsuario(Long usuarioId);





    /** Peticion with Response...*/
    Response guardarUsuario(CatUsuarioDTO catUsuario);
}
