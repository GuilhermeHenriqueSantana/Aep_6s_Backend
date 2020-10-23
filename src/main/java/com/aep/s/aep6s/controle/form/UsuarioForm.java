package com.aep.s.aep6s.controle.form;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aep.s.aep6s.modelos.Funcao;
import com.aep.s.aep6s.modelos.Usuario;
import com.sun.istack.NotNull;

public class UsuarioForm {
	
	@NotNull @NotEmpty
	private String userName;
	
	@NotNull @NotEmpty
	private String senha;
	
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty
	private String funcao;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
	public Usuario converter() {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Funcao funcao;
		
		switch (this.funcao) {
		case "ADMINISTRADOR":
			funcao = Funcao.ADMINISTRADOR;
			break;
		case "PROFESSOR":
			funcao = Funcao.PROFESSOR;
			break;
		default:
			funcao = Funcao.LIVRE;
			break;
		}
		
		return new Usuario(userName, passwordEncoder.encode(senha), nome, funcao);
	}
	
}
