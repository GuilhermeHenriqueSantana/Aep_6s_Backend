package com.aep.s.aep6s.controle.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Bloco;


public class BlocoForm {
	
	@NotNull @NotEmpty
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Bloco converter() {
		return new Bloco(nome);
	}
	
}
