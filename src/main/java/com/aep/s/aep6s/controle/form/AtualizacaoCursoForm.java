package com.aep.s.aep6s.controle.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Curso;
import com.aep.s.aep6s.repositorio.CursoRepositorio;

public class AtualizacaoCursoForm {
	
	@NotNull @NotEmpty
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Curso atualiza(Long id, CursoRepositorio cursoRepositorio) {
		Curso curso = cursoRepositorio.findById(id).get();
		curso.setNome(nome);
		return curso;
	}
}
