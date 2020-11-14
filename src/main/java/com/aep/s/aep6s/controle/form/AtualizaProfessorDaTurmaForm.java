package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Professor;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.ProfessorRepositorio;
import com.aep.s.aep6s.repositorio.TurmaRepositorio;

public class AtualizaProfessorDaTurmaForm {

	@NotNull
	private Long professorId;

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
	
	public Turma atualiza(Long id, TurmaRepositorio turmaRepositorio, ProfessorRepositorio professorRepositorio) throws Exception {
		
		Turma turma = turmaRepositorio.findById(id).get();
		
		Optional<Professor> professorOptional = professorRepositorio.findById(professorId);
		
		if (!professorOptional.isPresent()) {
			throw new Exception("Professor com id:" + professorId + " não encontrado");
		}
		
		Professor professor = professorOptional.get();
		
		turma.getProfessores().add(professor);
		professor.getTurmas().add(turma);
		
		return turma;
		
	}
	
}
