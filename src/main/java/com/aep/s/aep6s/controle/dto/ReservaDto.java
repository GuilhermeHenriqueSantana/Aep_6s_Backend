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
	
	private HorarioDto horarioDto;
	
	private TurmaDto turmaDto;
	
	private ProfessorDto professorDto;

	public ReservaDto(Reserva reserva) {
		this.id = reserva.getId();
		this.horarioReserva = reserva.getHorarioReserva();
		this.tipoReserva = reserva.getTipoReserva();
		this.horarioId = reserva.getHorario().getId();
		this.turmaId = reserva.getTurma().getId();
		this.horarioDto = new HorarioDto(reserva.getHorario());
		this.turmaDto = new TurmaDto(reserva.getTurma());
		this.professorDto = new ProfessorDto(reserva.getProfessor());
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
	
	public HorarioDto getHorarioDto() {
		return horarioDto;
	}
	
	public TurmaDto getTurmaDto() {
		return turmaDto;
	}
	
	public ProfessorDto getProfessorDto() {
		return professorDto;
	}
	
	public static List<ReservaDto> converter(List<Reserva> reservas) {
		List<ReservaDto> reservaDtos = new ArrayList<ReservaDto>();
		
		for (Reserva reserva : reservas) {
			reservaDtos.add(new ReservaDto(reserva));
		}
		
		return reservaDtos;
	}
	
	
	
	
}
