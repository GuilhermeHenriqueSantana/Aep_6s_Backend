package com.aep.s.aep6s.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aep.s.aep6s.modelos.Professor;

public interface ProfessorRepositorio extends JpaRepository<Professor, Long>{
	Optional<Professor> findByUsuarioUserName(String userName);
}
