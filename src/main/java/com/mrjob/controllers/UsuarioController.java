package com.mrjob.controllers;

import com.mrjob.dto.CatUsuarioDTO;
import com.mrjob.dto.Response;
import com.mrjob.entities.Roles.CatRol;
import com.mrjob.entities.Roles.UsuarioRol;
import com.mrjob.entities.usuarios.CatUsuario;
import com.mrjob.service.UsuarioService;
import com.mrjob.utilerias.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*") /** Para poder aceptar intercambio de recursos mediante http client, peticion GET, POST, ETC...  */
public class UsuarioController {
    /**
     * DEBUG: Usado para escribir mensajes de depuración
     * INFO: Mensajes de estilo verbose. Puramente informativos de determinada acción
     * WARN: Para alertar de eventos de los que se requiere dejar constancia pero que no afectan el funcionamiento de la aplicación
     * ERROR: Usado para los mensajes de eventos que afectan al programa pero lo dejan seguir funcionando. Algún parametro no es correcto
     *        pero se carga el parametro por defecto, por ejemplo.
     * FATAL: Usado para errores criticos. Normalmente después de guardar el mensaje el programa terminará.
     */

    private static Logger Logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

  //@PostMapping("/")
    @PostMapping(value = "/")  //addNewUserResp
    public Response guardarUsuario(@RequestBody CatUsuarioDTO catUsuario) throws Exception{
        try{
            return usuarioService.guardarUsuario(catUsuario);
        } catch (Exception e){
            Logger.info("Error en (UsuarioController) -> guardar usuario" + e.getMessage());
            return new Response(500,Messages.MS500);
        }
    }


    /* RESPALDO

    @PostMapping("/") // addNewUser
    public CatUsuario guardarUsuario(@RequestBody CatUsuario catUsuario) throws Exception{

        // Encriptamos la contraseña que trae del front el obj -> usuarioEntity
        catUsuario.setPassword(this.bCryptPasswordEncoder.encode(catUsuario.getPassword()));
        catUsuario.setNombre_completo(catUsuario.getNombre()+" "+catUsuario.getPrimer_apellido()+" "+catUsuario.getSegundo_apellido());

        Set<UsuarioRol> usuarioRoles = new HashSet<>();

        CatRol rol = new CatRol();
        rol.setIdRol(15L); //Representar datos -> L=Long
        rol.setNombreRol("Test()-Jobers");
        rol.setDescripcion("TEST()-Este rol de Jobers son los Clientes");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(catUsuario);
        usuarioRol.setRol(rol);

        usuarioRoles.add(usuarioRol);

        return usuarioService.guardarUsuario(catUsuario, usuarioRoles);
    }
     */

    /**
     * Get user - username
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public CatUsuario obtenerUsuario(@PathVariable("username") String username) {
        return usuarioService.obtenerUsuario(username);
    }

    /**
     * Update dates user
     * @param categoria
     * @return
     */
    @PutMapping("/updateUser")
    public CatUsuario actualizarUsuario(@RequestBody CatUsuario categoria){
        return usuarioService.actualizarCategoria(categoria);
    }


    /*@PutMapping(value = "/updateUser")
    public Response actualizarCategoriaTest(@RequestBody CatUsuario categoria){
        try {
            return usuarioService.actualizarCategoria(categoria);
        } catch (Exception e){
            //Logger.info("Error en (UsuarioController) -> actualizar usuarios" + e.getMessage());
            return new Response(500, "Error...!!!");
        }
    }*/


    /**
     * Delete user - usuarioId
     * @param usuarioId
     */
    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {

        usuarioService.eliminarUsuario(usuarioId);
    }

}
