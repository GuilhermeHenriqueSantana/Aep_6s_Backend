package com.aep.s.aep6s.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aep.s.aep6s.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByUserName(String userName);
	
}
