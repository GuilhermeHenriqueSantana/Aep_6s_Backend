package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.HorarioReserva;
import com.aep.s.aep6s.modelos.Reserva;
import com.aep.s.aep6s.modelos.TipoReserva;

public class ReservaDto {
	
	private Long id;
	
	private HorarioReserva horarioReserva;
	
	private TipoReserva tipoReserva;
	
	private Long horarioId;
	
	private Long turmaId;

	public ReservaDto(Reserva reserva) {
		this.id = reserva.getId();
		this.horarioReserva = reserva.getHorarioReserva();
		this.tipoReserva = reserva.getTipoReserva();
		this.horarioId = reserva.getHorario().getId();
		this.turmaId = reserva.getTurma().getId();
	}

	public Long getId() {
		return id;
	}

	public HorarioReserva getHorarioReserva() {
		return horarioReserva;
	}

	public TipoReserva getTipoReserva() {
		return tipoReserva;
	}

	public Long getHorarioId() {
		return horarioId;
	}

	public Long getTurmaId() {
		return turmaId;
	}
	
	public static List<ReservaDto> converter(List<Reserva> reservas) {
		List<ReservaDto> reservaDtos = new ArrayList<ReservaDto>();
		
		for (Reserva reserva : reservas) {
			reservaDtos.add(new ReservaDto(reserva));
		}
		
		return reservaDtos;
	}
	
	
	
	
}
