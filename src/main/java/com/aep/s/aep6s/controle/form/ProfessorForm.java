package com.aep.s.aep6s.controle.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Professor;
import com.aep.s.aep6s.modelos.Usuario;
import com.aep.s.aep6s.repositorio.UsuarioRepositorio;

public class ProfessorForm {
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull
	private int ra;
	
	@NotNull
	private Long usuarioId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getRa() {
		return ra;
	}

	public void setRa(int ra) {
		this.ra = ra;
	}
	
	public Long getUsuarioId() {
		return usuarioId;
	}
	
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public Professor converter(UsuarioRepositorio usuarioRepositorio) throws Exception {
		Optional<Usuario> optional = usuarioRepositorio.findById(usuarioId);
		
		if (!optional.isPresent()) {
			throw new Exception("Usuario com id:" + usuarioId + " não encontrado");
		}
		
		return new Professor(nome, ra, optional.get());
	}
	
}
