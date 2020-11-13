package com.aep.s.aep6s.controle.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Professor;

public class ProfessorForm {
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull
	private int ra;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getRa() {
		return ra;
	}

	public void setRa(int ra) {
		this.ra = ra;
	}
	
	public Professor converter() {
		return new Professor(nome, ra);
	}
	
}
