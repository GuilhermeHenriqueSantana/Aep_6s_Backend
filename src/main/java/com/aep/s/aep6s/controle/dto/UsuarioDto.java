package com.aep.s.aep6s.controle.dto;

import java.util.ArrayList;
import java.util.List;

import com.aep.s.aep6s.modelos.Usuario;

public class UsuarioDto {
	
	private Long id;
	
	private String userName;
	
	private String nome;
	
	private String funcao;

	public UsuarioDto(Usuario usuario) {
		this.userName = usuario.getUserName();
		this.nome = usuario.getNome();
		this.funcao = usuario.getFuncao().toString();
		this.id = usuario.getId();
	}

	public String getUserName() {
		return userName;
	}

	public String getNome() {
		return nome;
	}

	public String getFuncao() {
		return funcao;
	}
	
	public Long getId() {
		return id;
	}
	
	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		List<UsuarioDto> usuarioDtos = new ArrayList<UsuarioDto>();
		for (Usuario usuario : usuarios) {
			usuarioDtos.add(new UsuarioDto(usuario));
		}
		return usuarioDtos;
	}
	
	
}
