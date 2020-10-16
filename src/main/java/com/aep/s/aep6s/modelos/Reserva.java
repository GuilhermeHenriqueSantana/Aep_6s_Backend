package com.aep.s.aep6s.modelos;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private HorarioReserva horarioReserva;
	
	@Enumerated(EnumType.STRING)
	private TipoReserva tipoReserva;
	
	@ManyToOne
	private Horario horario;
	
	@ManyToOne
	private Turma turma;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public Horario getHorario() {
		return horario;
	}
	
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
}
