package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.Laboratorio;

public class LaboratorioDto {
	
	private Long id;
	
	private String nome;
	
	private Long blocoId;
	
	private List<Long> horariosId;

	public LaboratorioDto(Laboratorio laboratorio) {
		this.id = laboratorio.getId();
		this.nome = laboratorio.getNome();
		this.blocoId = laboratorio.getBloco().getId();
		this.horariosId = new ArrayList<Long>();
		laboratorio.getHorarios().forEach(x -> {
			this.horariosId.add(x.getId());
		});
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Long getBlocoId() {
		return blocoId;
	}

	public List<Long> getHorariosId() {
		return horariosId;
	}
	
	public static List<LaboratorioDto> converter(List<Laboratorio> laboratorios) {
		List<LaboratorioDto> laboratorioDtos = new ArrayList<>();
		
		for (Laboratorio laboratorio : laboratorios) {
			laboratorioDtos.add(new LaboratorioDto(laboratorio));
		}
		
		return laboratorioDtos;
	}
	
	
}
