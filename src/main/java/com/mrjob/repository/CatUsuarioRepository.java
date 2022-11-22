package com.mrjob.repository;

import com.mrjob.entities.usuarios.CatUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatUsuarioRepository extends JpaRepository<CatUsuario, Long> {

    public CatUsuario findByUsername(String username);

}
