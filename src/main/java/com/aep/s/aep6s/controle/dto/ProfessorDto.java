package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.Professor;

public class ProfessorDto {
	
	private Long id;
	
	private String nome;
	
	private int ra;
	
	private Long usuarioId;
	
	private List<Long> turmasId = new ArrayList<Long>();

	public ProfessorDto(Professor professor) {
		this.id = professor.getId();
		this.nome = professor.getNome();
		this.ra = professor.getRa();
		this.usuarioId = professor.getUsuario().getId();
		
		professor.getTurmas().forEach((turma)->{
			turmasId.add(turma.getId());
		});
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getRa() {
		return ra;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}

	public List<Long> getTurmasId() {
		return turmasId;
	}
	
	public static List<ProfessorDto> converter(List<Professor> professores) {
		List<ProfessorDto> professorDtos = new ArrayList<>();
		
		professores.forEach((professor)->{
			professorDtos.add(new ProfessorDto(professor));
		});
		
		return professorDtos;
	}
	
	
}
