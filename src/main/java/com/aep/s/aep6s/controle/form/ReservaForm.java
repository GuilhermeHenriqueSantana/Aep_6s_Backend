package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.HorarioReserva;
import com.aep.s.aep6s.modelos.Reserva;
import com.aep.s.aep6s.modelos.TipoReserva;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.TurmaRepositorio;

public class ReservaForm {

	@NotNull
	private HorarioReserva horarioReserva;
	
	@NotNull
	private TipoReserva tipoReserva;
	
	@NotNull
	private Long horarioId;
	
	@NotNull
	private Long turmaId;

	public HorarioReserva getHorarioReserva() {
		return horarioReserva;
	}

	public void setHorarioReserva(HorarioReserva horarioReserva) {
		this.horarioReserva = horarioReserva;
	}

	public TipoReserva getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(TipoReserva tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public Long getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(Long horarioId) {
		this.horarioId = horarioId;
	}

	public Long getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}
	
	public Reserva converter(HorarioRepositorio horarioRepositorio, TurmaRepositorio turmaRepositorio) throws Exception {
		
		Optional<Horario> horarioOpt = horarioRepositorio.findById(horarioId);
		
		Optional<Turma> turmaOpt = turmaRepositorio.findById(turmaId);
		
		if (!horarioOpt.isPresent()) {
			throw new Exception("Horario com id:" + horarioId + " não foi encontrado");
		}
		
		if (!turmaOpt.isPresent()) {
			throw new Exception("Turma com id:" + turmaId + " não foi encontrado");
		}
		
		Horario horario = horarioOpt.get();
		Turma turma = turmaOpt.get();
		
		Reserva reserva = new Reserva(horarioReserva, tipoReserva, horario, turma);
		
		horario.getReservas().add(reserva);
		turma.getReservas().add(reserva);
		
		return reserva;
		
	}
	
}
