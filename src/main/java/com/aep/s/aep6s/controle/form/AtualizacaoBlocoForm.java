package com.aep.s.aep6s.controle.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;



public class AtualizacaoBlocoForm {
	
	@NotNull @NotEmpty
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Bloco atualiza(Long id, BlocoRepositorio blocoRepositorio) {
		
		Bloco bloco = blocoRepositorio.getOne(id);
		bloco.setNome(nome);
		
		return bloco;
	}
	
}
