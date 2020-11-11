package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.Turno;

public class HorarioDto {
	
	private Long id;
	
	private String dataTimestamp;
	
	private Turno turno;
	
	private Long laboratorioId;
	
	private List<Long> reservasIds;

	public HorarioDto(Horario horario) {
		this.id = horario.getId();

		this.turno = horario.getTurno();
		this.laboratorioId = horario.getLaboratorio().getId();
		this.reservasIds = new ArrayList<Long>();
		horario.getReservas().forEach(x -> {
			this.reservasIds.add(x.getId());
		});
		
		Date date = horario.getData().getTime();
		this.dataTimestamp = new Long(date.getTime()).toString();
	}

	public Long getId() {
		return id;
	}

	public String getDataTimestamp() {
		return dataTimestamp;
	}

	public Turno getTurno() {
		return turno;
	}

	public Long getLaboratorioId() {
		return laboratorioId;
	}

	public List<Long> getReservasIds() {
		return reservasIds;
	}
	
	public static List<HorarioDto> converter(List<Horario> horarios) {
		List<HorarioDto> horarioDtos = new ArrayList<>();
		
		for (Horario horario : horarios) {
			horarioDtos.add(new HorarioDto(horario));
		}
		
		return horarioDtos;
	}
	
	
	
}
