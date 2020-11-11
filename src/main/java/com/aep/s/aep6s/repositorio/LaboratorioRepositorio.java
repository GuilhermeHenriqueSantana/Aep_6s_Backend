package com.aep.s.aep6s.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aep.s.aep6s.modelos.Laboratorio;

public interface LaboratorioRepositorio extends JpaRepository<Laboratorio, Long>{

	Optional<List<Laboratorio>> findByNome(String nome);

}
