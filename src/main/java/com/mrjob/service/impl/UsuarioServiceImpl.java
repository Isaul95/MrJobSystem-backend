package com.mrjob.service.impl;

import com.mrjob.dto.CatUsuarioDTO;
import com.mrjob.dto.Response;
import com.mrjob.entities.Negocios.CatNegocios;
import com.mrjob.entities.Negocios.UsuarioNegocios;
import com.mrjob.entities.Roles.CatRol;
import com.mrjob.entities.Roles.UsuarioRol;
import com.mrjob.entities.Servicios.CatServicios;
import com.mrjob.entities.Servicios.UsuarioServicios;
import com.mrjob.entities.usuarios.CatUsuario;
import com.mrjob.excepciones.UsuarioFoundException;
import com.mrjob.repository.CatNegociosRepository;
import com.mrjob.repository.CatRolRepository;
import com.mrjob.repository.CatServiciosRepository;
import com.mrjob.repository.CatUsuarioRepository;
import com.mrjob.service.UsuarioService;
import com.mrjob.utilerias.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static Logger Logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private CatUsuarioRepository catUsuarioRepository;
    @Autowired
    private CatRolRepository catRolRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CatServiciosRepository catServiciosRepository;

    @Autowired
    private CatNegociosRepository catNegociosRepository;

    /**
     * Save user
     * @param usuario
     * @param usuarioRoles
     * @return
     */
    @Override
    public CatUsuarioDTO guardarUsuario(CatUsuarioDTO usuario, Set<UsuarioRol> usuarioRoles) throws Exception {

        CatUsuario existeUsuario = catUsuarioRepository.findByUsername(usuario.getUsername());

        if(existeUsuario != null){
            System.out.println("El usuario ya Existe...!");
            throw new UsuarioFoundException("El usuario ya Existe...!");
        }else{
            for (UsuarioRol userRol:usuarioRoles){ // Recorremos roles del conjunto de -> Set<UsuarioRol>
                catRolRepository.save(userRol.getRol());
            }
        }
        //usuario.getUsuarioRoles().addAll(usuarioRoles);
        //existeUsuario = catUsuarioRepository.save(usuario); // Le asignamos todo_a usuarioLocal

      //return existeUsuario;
        return usuario;
    }


    /* ---------- *******************   CON RESPONSE METHOD   **********************---- ISAUL ------------  */
    @Override
    public Response guardarUsuario(CatUsuarioDTO catUsuarioDTO) {
        Response response = new Response();
        CatRol rol = new CatRol();
        CatUsuario user = new CatUsuario();
        UsuarioRol usuarioRol = new UsuarioRol();
        UsuarioServicios usuarioService = new UsuarioServicios();
        UsuarioNegocios usuarioNegocio = new UsuarioNegocios();
        CatServicios serv= null;
        CatNegocios negoc= null;

        try {
            CatUsuario existeUsuario = catUsuarioRepository.findByUsername(catUsuarioDTO.getUsername());

            if(existeUsuario != null){
                Logger.info("El usuario ya Existe en la DB...!");
                throw new UsuarioFoundException("El usuario ya Existe...!");
            }else{

                if (catUsuarioDTO.getTipoServicioOfrece() == null || catUsuarioDTO.getTipoServicioOfrece().isEmpty() &&
                        catUsuarioDTO.getNombreNegocio() == null || catUsuarioDTO.getNombreNegocio().isEmpty()) {
                    //JOBERS_(clientes)
                  // rol.setIdRol(1L);
                    user.setIdRol(1L); /** ----------- Isaul agregando ir rol en la tabla de usuarios...........*/
                }else{
                    Logger.info("El usuario es JOB_2022...!");

                    CatServicios servicios = new CatServicios();
                        servicios.setNombre_servicio(catUsuarioDTO.getTipoServicioOfrece());
                        servicios.setDescripcion("TEST CODE.......Descripcion");
                        serv = catServiciosRepository.save(servicios);

                    CatNegocios negocio = new CatNegocios();
                        negocio.setIdServicios(serv.getId_servicio()); /** ----------- Isaul agregando ir negocio en la tabla de usuarios...........*/
                        negocio.setNombre(catUsuarioDTO.getNombreNegocio());
                        negocio.setDomicilio("TEST CODE.......Domicilio");
                        negocio.setDescripcion("TEST CODE.....Descripcion");
                        negoc = catNegociosRepository.save(negocio);

                    // JOB_(Prestador de servicio)
                    // rol.setIdRol(2L);
                      user.setIdRol(2L);


                    /* Usuario to nname de su negocios */
                /*usuarioNegocio.setUsuario(user);
                usuarioNegocio.setNegocio(negoc);
                    Set<UsuarioNegocios> usuarioNegoc = new HashSet<>();
                    usuarioNegoc.add(usuarioNegocio);*/

                    user.setIdNegocios(negoc.getId()); /** ----------- Isaul agregando ir negocio en la tabla de usuarios...........*/
                }
                //user.setId_usuario(catUsuarioDTO.getId_usuario());
                user.setNombre(catUsuarioDTO.getNombre());
                user.setPrimer_apellido(catUsuarioDTO.getPrimer_apellido());
                user.setSegundo_apellido(catUsuarioDTO.getSegundo_apellido());
                user.setEmail(catUsuarioDTO.getEmail());
                user.setActivo(catUsuarioDTO.getActivo());
                user.setTelefono(catUsuarioDTO.getTelefono());
                user.setUsername(catUsuarioDTO.getUsername());

                /* Usuario to rol */
                /*usuarioRol.setUsuario(user);
                usuarioRol.setRol(rol);
                    Set<UsuarioRol> usuarioRoles = new HashSet<>();
                    usuarioRoles.add(usuarioRol);*/

                /* Usuario to servicios que ofrecen */
                /*usuarioService.setUsuario(user);
                usuarioService.setServicio(serv);
                    Set<UsuarioServicios> usuarioServic = new HashSet<>();
                    usuarioServic.add(usuarioService);*/


                // Encriptamos la contraseña que trae del front el obj -> usuarioEntity
                user.setPassword(this.bCryptPasswordEncoder.encode(catUsuarioDTO.getPassword()));
                user.setNombre_completo(catUsuarioDTO.getNombre()+" "+catUsuarioDTO.getPrimer_apellido()+" "+catUsuarioDTO.getSegundo_apellido());

                //user.getUsuarioRoles().addAll(usuarioRoles);
                //user.getUsuarioServicios().addAll(usuarioServic);
                //user.getUsuarioNegocios().addAll(usuarioNegoc);
                existeUsuario = catUsuarioRepository.save(user);

                Logger.info("Usuario guardado exitosamente...!");
                response.setCode(200);
                response.setDescripcion(Messages.MSEXITO_GUADAR_USER);
            }
        } catch (Exception e){
            response.setCode(500);
            response.setDescripcion(Messages.MSERROR_GUARDAR_USER);
            Logger.error("Error en (UsuarioServiceImpl.Clas) -> guardarUsuario");
        }
        return response;
    }



    /**
     * Get user
     * @param usuario
     * @return
     */
    @Override
    public CatUsuario obtenerUsuario(String usuario) {
        return catUsuarioRepository.findByUsername(usuario);
    }

    /**
     * Update user
     * @param usuario
     * @return
     */
    @Override
    public CatUsuario actualizarCategoria(CatUsuario usuario) {
        return catUsuarioRepository.save(usuario);
    }


    /**
     * Delete user
     * @param usuarioId
     */
    @Override
    public void eliminarUsuario(Long usuarioId) {
        catUsuarioRepository.deleteById(usuarioId);
    }

}
