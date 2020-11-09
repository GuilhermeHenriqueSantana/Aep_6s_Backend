package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;

public class AtualizacaoLaboratorioForm {
	
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

	public void setBlocoId(Long blocoId) {
		this.blocoId = blocoId;
	}
	
	public Laboratorio atualiza(Long id, BlocoRepositorio blocoRepositorio, LaboratorioRepositorio laboratorioRepositorio) throws Exception {
		
		Laboratorio laboratorio = laboratorioRepositorio.findById(id).get();
		
		Bloco antigo = blocoRepositorio.findById(laboratorio.getBloco().getId()).get();
		
		Optional<Bloco> novoOpt =  blocoRepositorio.findById(blocoId);
		
		if (!novoOpt.isPresent()) {
			throw new Exception("bloco com id:" + blocoId + " não encontrado");
		}
		
		Bloco novo = novoOpt.get();
		
		if (novo.getId() != antigo.getId()) {
			antigo.getLaboratorios().remove(laboratorio);
			novo.getLaboratorios().add(laboratorio);
			laboratorio.setBloco(novo);
		}
		
		laboratorio.setNome(nome);
		
		return laboratorio;
	}
}
