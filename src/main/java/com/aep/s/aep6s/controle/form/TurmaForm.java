package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Curso;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.CursoRepositorio;

public class TurmaForm {
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull
	private int quantidadeAlunos;
	
	@NotNull
	private Long cursoId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidadeAlunos() {
		return quantidadeAlunos;
	}

	public void setQuantidadeAlunos(int quantidadeAlunos) {
		this.quantidadeAlunos = quantidadeAlunos;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}
	
	public Turma converter(CursoRepositorio cursoRepositorio) throws Exception {
		
		Optional<Curso> cursoOptional = cursoRepositorio.findById(cursoId);
		
		if (!cursoOptional.isPresent()) {
			throw new Exception("curso com id:" + cursoId + " não encontrado");
		}
		
		Curso curso = cursoOptional.get();
		
		Turma turma = new Turma(nome, quantidadeAlunos, curso);
		
		curso.getTurmas().add(turma);
		
		return turma;	
		
	}
}
