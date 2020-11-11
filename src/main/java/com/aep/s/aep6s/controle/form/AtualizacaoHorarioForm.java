package com.aep.s.aep6s.controle.form;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.modelos.Turno;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;

public class AtualizacaoHorarioForm {
	
	@NotNull 
	private long data;
	
	@NotNull @NotEmpty
	private String turno;
	
	@NotNull
	private Long laboratorioId;

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Long getLaboratorioId() {
		return laboratorioId;
	}

	public void setLaboratorioId(Long laboratorioId) {
		this.laboratorioId = laboratorioId;
	}
	
	public Horario atualiza(Long id, HorarioRepositorio horarioRepositorio, LaboratorioRepositorio laboratorioRepositorio) throws Exception {
		Horario horario = horarioRepositorio.findById(id).get();
		
		Laboratorio antigo = laboratorioRepositorio.findById(horario.getLaboratorio().getId()).get();
		
		Optional<Laboratorio> novoOpt = laboratorioRepositorio.findById(laboratorioId);
		
		if (!novoOpt.isPresent()) {
			throw new Exception("Laboratorio com id: " + laboratorioId + " não encontrado");
		}
		
		Laboratorio novo = novoOpt.get();
		
		if (novo.getId() != antigo.getId()) {
			antigo.getHorarios().remove(horario);
			novo.getHorarios().add(horario);
			horario.setLaboratorio(novo);
		}
		
		Long timestamp = new Long(data);
		Date data = new Date(timestamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		
		Turno turnoConvertido;
		
		switch (turno) {
		case "MANHA":
			turnoConvertido = Turno.MANHA;
			break;
		case "TARDE":
			turnoConvertido = Turno.TARDE;
			break;
		default:
			turnoConvertido = Turno.NOITE;
			break;
		}
		
		horario.setData(calendar);
		horario.setTurno(turnoConvertido);
		
		return horario;
	}
	
}
