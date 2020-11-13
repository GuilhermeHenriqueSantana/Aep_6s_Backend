package com.aep.s.aep6s.controle.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Professor;
import com.aep.s.aep6s.repositorio.ProfessorRepositorio;

public class AtualizacaoProfessorForm {
	
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
	
	public Professor atualiza(Long id, ProfessorRepositorio professorRepositorio) {
		
		Professor professor = professorRepositorio.findById(id).get();
		
		professor.setNome(nome);
		
		professor.setRa(ra);
		
		return professor;
	}
}
