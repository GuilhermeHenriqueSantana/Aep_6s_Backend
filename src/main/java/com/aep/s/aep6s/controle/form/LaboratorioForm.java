package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;


public class LaboratorioForm {
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull 
	private Long blocoId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getBlocoId() {
		return blocoId;
	}

	public void setBloco(Long blocoId) {
		this.blocoId = blocoId;
	}
	
	public Laboratorio converter(BlocoRepositorio blocoRepositorio) throws Exception {
		
		Optional<Bloco> blocoOpt = blocoRepositorio.findById(blocoId);
		
		Bloco bloco;
		
		if (blocoOpt.isPresent()) {
			bloco = blocoOpt.get();
		} else {
			throw new Exception("Bloco com id:" + blocoId + " não encontrado");
		}
		
		Laboratorio laboratorio = new Laboratorio(nome, bloco);
		
		bloco.getLaboratorios().add(laboratorio);

		return laboratorio;
	}
}
