package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.Curso;


public class CursoDto {

	private Long id;
	
	private String nome;
	
	List<Long> turmasIds = new ArrayList<Long>();

	public CursoDto(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		curso.getTurmas().forEach((x) -> {
			this.turmasIds.add(x.getId());
		});;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<Long> getTurmasIds() {
		return turmasIds;
	}
	
	public static List<CursoDto> converter(List<Curso> cursos) {
		List<CursoDto> cursoDtos = new ArrayList<>();
		
		cursos.forEach((curso)->{
			cursoDtos.add(new CursoDto(curso));
		});
		
		return cursoDtos;
	}
	
	
}
