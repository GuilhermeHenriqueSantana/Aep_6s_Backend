package com.aep.s.aep6s.controle.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;
import com.sun.istack.NotNull;

public class AtualizacaoBlocoForm {
	
	@NotNull @NotEmpty
	private String nome;
	
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
	
	public Bloco atualiza(Long id, BlocoRepositorio blocoRepositorio, LaboratorioRepositorio laboratorioRepositorio) {
		
		List<Laboratorio> laboratorios = new ArrayList<Laboratorio>();
		
		if (laboratoriosId.length > 0) {
			for (Long id2 : laboratoriosId) {
				Optional<Laboratorio> opLab = laboratorioRepositorio.findById(id2);
				if (opLab.isPresent()) {
					laboratorios.add(opLab.get());
				}
			}
		}
		
		Bloco bloco = blocoRepositorio.getOne(id);
		bloco.setNome(nome);
		bloco.setLaboratorios(laboratorios);
		
		return bloco;
	}
	
}
