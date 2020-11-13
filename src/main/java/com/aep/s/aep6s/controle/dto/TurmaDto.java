package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.Turma;

public class TurmaDto {
	
	private Long id;
	
	private String nome;
	
	private int quantidadeAlunos;
	
	private Long cursoId;
	
	private List<Long> reservasId = new ArrayList<Long>();;
	
	private List<Long> professoresId = new ArrayList<Long>();

	public TurmaDto(Turma turma) {
		this.id = turma.getId();
		this.nome = turma.getNome();
		this.quantidadeAlunos = turma.getQuantidadeAlunos();
		this.cursoId = turma.getCurso().getId();
		
		turma.getReservas().forEach((reserva)->{
			reservasId.add(reserva.getId());
		});
		
		turma.getProfessores().forEach((professor)->{
			professoresId.add(professor.getId());
		});
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getQuantidadeAlunos() {
		return quantidadeAlunos;
	}
	
	public Long getCursoId() {
		return cursoId;
	}

	public List<Long> getReservasId() {
		return reservasId;
	}

	public List<Long> getProfessoresId() {
		return professoresId;
	}
	
	public static List<TurmaDto> converter(List<Turma> turmas) {
		List<TurmaDto> turmaDtos = new ArrayList<>();
		
		turmas.forEach((turma)->{
			turmaDtos.add(new TurmaDto(turma));
		});
		
		return turmaDtos;
	}
}
