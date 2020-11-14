package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.HorarioReserva;
import com.aep.s.aep6s.modelos.Reserva;
import com.aep.s.aep6s.modelos.TipoReserva;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.ReservaRepositorio;
import com.aep.s.aep6s.repositorio.TurmaRepositorio;

public class AtualizacaoReservaForm {
	
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
	
	public Reserva atualiza(Long id, HorarioRepositorio horarioRepositorio, TurmaRepositorio turmaRepositorio, ReservaRepositorio reservaRepositorio) throws Exception {
		
		Reserva reserva = reservaRepositorio.getOne(id);
		
		Optional<Horario> horarioNovoOpt = horarioRepositorio.findById(horarioId);
		
		Optional<Turma> turmaNovoOpt = turmaRepositorio.findById(turmaId);
		
		if (!horarioNovoOpt.isPresent()) {
			throw new Exception("Horario com id:" + horarioId + " não foi encontrado");
		}
		
		if (!turmaNovoOpt.isPresent()) {
			throw new Exception("Turma com id:" + turmaId + " não foi encontrado");
		}
		
		Horario horarioAntigo = horarioRepositorio.getOne(reserva.getHorario().getId());
		Horario horarioNovo = horarioNovoOpt.get();
		
		Turma turmaAntigo = turmaRepositorio.getOne(reserva.getTurma().getId());
		Turma turmaNovo = turmaNovoOpt.get();
		
		if (horarioNovo.getId() != horarioAntigo.getId()) {
			horarioAntigo.getReservas().remove(reserva);
			horarioNovo.getReservas().add(reserva);
			reserva.setHorario(horarioNovo);
		}
		
		if (turmaNovo.getId() != turmaAntigo.getId()) {
			turmaAntigo.getReservas().remove(reserva);
			turmaNovo.getReservas().add(reserva);
			reserva.setTurma(turmaNovo);
		}
		
		reserva.setHorarioReserva(horarioReserva);
		reserva.setTipoReserva(tipoReserva);
		
		return reserva;
	}
}
