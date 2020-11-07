package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.Bloco;

public class BlocoDto {
	
	private Long id;
	
	private String nome;
	
	private List<Long> laboratoriosId;

	public BlocoDto(Bloco bloco) {
		this.id = bloco.getId();
		this.nome = bloco.getNome();
		this.laboratoriosId = new ArrayList<Long>();
		bloco.getLaboratorios().forEach(x -> {
			this.laboratoriosId.add(x.getId());
		});
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<Long> getLaboratoriosId() {
		return laboratoriosId;
	}
	
	public static List<BlocoDto> converter(List<Bloco> blocos) {
		List<BlocoDto> blocoDtos = new ArrayList<BlocoDto>();
		for (Bloco bloco : blocos) {
			blocoDtos.add(new BlocoDto(bloco));
		}
		return blocoDtos;
	}
	
	
}
