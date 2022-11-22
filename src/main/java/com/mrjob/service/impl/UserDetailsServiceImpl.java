package com.mrjob.service.impl;

import com.mrjob.entities.usuarios.CatUsuario;
import com.mrjob.repository.CatUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Esta clase es parte del JWT token, consulta el usuario que ingresa...
     */
    @Autowired
    private CatUsuarioRepository catUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CatUsuario usuario = this.catUsuarioRepository.findByUsername(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

}
