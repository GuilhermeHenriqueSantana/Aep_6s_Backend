package com.aep.s.aep6s.controle.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.aep.s.aep6s.modelos.Funcao;
import com.aep.s.aep6s.modelos.Usuario;
import com.aep.s.aep6s.repositorio.UsuarioRepositorio;
import com.aep.s.aep6s.validacao.FuncaoValid;


public class AtualizacaoUsuarioForm {
		
	@NotNull @NotEmpty
	private String nome;
	
	@NotNull @NotEmpty @FuncaoValid
	private String funcao;

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
	
	public Usuario atualiza(Long id, UsuarioRepositorio usuarioRepositorio) {
		
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
		
		Usuario usuario = usuarioRepositorio.getOne(id);
		
		usuario.setFuncao(funcao);
		usuario.setNome(nome);
		
		return usuario;
	}
	
}
