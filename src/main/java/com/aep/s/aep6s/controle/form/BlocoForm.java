package com.aep.s.aep6s.controle.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;
import com.sun.istack.NotNull;

public class BlocoForm {
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull
	private Long[] laboratoriosId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long[] getLaboratoriosId() {
		return laboratoriosId;
	}

	public void setLaboratoriosId(Long[] laboratoriosId) {
		this.laboratoriosId = laboratoriosId;
	}
	
	public Bloco converter(LaboratorioRepositorio laboratorioRepositorio) {
		
		List<Laboratorio> laboratorios = new ArrayList<Laboratorio>();
		
		if (laboratoriosId.length > 0) {
			for (Long id : laboratoriosId) {
				Optional<Laboratorio> opLab = laboratorioRepositorio.findById(id);
				if (opLab.isPresent()) {
					laboratorios.add(opLab.get());
				}
			}
		}
		
		return new Bloco(nome, laboratorios);
	}
	
}
