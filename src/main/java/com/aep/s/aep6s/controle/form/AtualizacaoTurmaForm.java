package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Curso;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.CursoRepositorio;
import com.aep.s.aep6s.repositorio.TurmaRepositorio;

public class AtualizacaoTurmaForm {
	
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
	
	public Turma atualiza(Long id, TurmaRepositorio turmaRepositorio, CursoRepositorio cursoRepositorio) throws Exception {
		
		Turma turma = turmaRepositorio.findById(id).get();
		
		Curso antigo = cursoRepositorio.findById(turma.getCurso().getId()).get();
		
		Optional<Curso> cursoOptional = cursoRepositorio.findById(cursoId);
		
		if (!cursoOptional.isPresent()) {
			throw new Exception("Curso com id:" + cursoId + " não encontrado");
		}
		
		Curso novo = cursoOptional.get();
		
		if (novo.getId() != antigo.getId()) {
			antigo.getTurmas().remove(turma);
			novo.getTurmas().add(turma);
			turma.setCurso(novo);
		}
		
		turma.setNome(nome);
		
		turma.setQuantidadeAlunos(quantidadeAlunos);
		
		return turma;
		
	}
}
