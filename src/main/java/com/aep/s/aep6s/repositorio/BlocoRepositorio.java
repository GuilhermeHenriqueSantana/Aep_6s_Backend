package com.aep.s.aep6s.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aep.s.aep6s.modelos.Bloco;

public interface BlocoRepositorio extends JpaRepository<Bloco, Long>{
	Optional<List<Bloco>> findByNome(String nome);
}
