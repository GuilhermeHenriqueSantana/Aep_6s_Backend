package com.aep.s.aep6s.controle.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;


public class LaboratorioForm {
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull 
	private Long blocoId;
	
	@NotNull
	private Long[] horariosId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getBlocoId() {
		return blocoId;
	}

	public void setBloco(Long blocoId) {
		this.blocoId = blocoId;
	}

	public Long[] getHorariosId() {
		return horariosId;
	}

	public void setHorariosId(Long[] horariosId) {
		this.horariosId = horariosId;
	}
	
	public Laboratorio converter(BlocoRepositorio blocoRepositorio, HorarioRepositorio horarioRepositorio) throws Exception {
		
		Optional<Bloco> blocoOpt = blocoRepositorio.findById(blocoId);
		
		Bloco bloco;
		
		if (blocoOpt.isPresent()) {
			bloco = blocoOpt.get();
		} else {
			throw new Exception("Bloco com id:" + blocoId + " não encontrado");
		}
		
		
		List<Horario> horarios = new ArrayList<>();
		
		if (horariosId.length > 0) {
			for (Long id : horariosId) {
				Optional<Horario> opHorario = horarioRepositorio.findById(id);
				if (opHorario.isPresent()) {
					horarios.add(opHorario.get());
				}
			}
		}

		return new Laboratorio(nome, bloco, horarios);
	}
}
