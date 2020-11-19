package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.HorarioReserva;
import com.aep.s.aep6s.modelos.Professor;
import com.aep.s.aep6s.modelos.Reserva;
import com.aep.s.aep6s.modelos.TipoReserva;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.ProfessorRepositorio;
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
	
	@NotNull
	private Long professorId;

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
	
	public Long getProfessorId() {
		return professorId;
	}
	
	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
	
	public Reserva converter(HorarioRepositorio horarioRepositorio, TurmaRepositorio turmaRepositorio, ProfessorRepositorio professorRepositorio) throws Exception {
		
		Optional<Professor> professorOpt = professorRepositorio.findById(professorId);
		
		Optional<Horario> horarioOpt = horarioRepositorio.findById(horarioId);
		
		Optional<Turma> turmaOpt = turmaRepositorio.findById(turmaId);
		
		if (!horarioOpt.isPresent()) {
			throw new Exception("Horario com id:" + horarioId + " não foi encontrado");
		}
		
		if (!turmaOpt.isPresent()) {
			throw new Exception("Turma com id:" + turmaId + " não foi encontrado");
		}
		
		if (!professorOpt.isPresent()) {
			throw new Exception("Professor com id:" + professorId + " não foi encontrado");
		}
		
		Horario horario = horarioOpt.get();
		Turma turma = turmaOpt.get();
		Professor professor = professorOpt.get();
		
		Reserva reserva = new Reserva(horarioReserva, tipoReserva, horario, turma, professor);
		
		horario.getReservas().add(reserva);
		turma.getReservas().add(reserva);
		
		return reserva;
		
	}
	
}
