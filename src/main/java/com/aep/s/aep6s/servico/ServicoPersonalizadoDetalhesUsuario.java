package com.aep.s.aep6s.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.aep.s.aep6s.modelos.Usuario;
import com.aep.s.aep6s.repositorio.UsuarioRepositorio;

@Component
public class ServicoPersonalizadoDetalhesUsuario implements UserDetailsService{
	private final UsuarioRepositorio usuarioRepositorio;

	@Autowired
	public ServicoPersonalizadoDetalhesUsuario(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optionalUsuario =  usuarioRepositorio.findByUserName(username);
		if (!optionalUsuario.isPresent()) {
			throw new UsernameNotFoundException("Usuario não encontrado");			
		}
		Usuario user = optionalUsuario.get();
		List<GrantedAuthority> authoritiesAdmin = AuthorityUtils.createAuthorityList("ROLE_USUARIO", "ROLE_PROFESSOR", "ROLE_ADMINISTRADOR");
		List<GrantedAuthority> authoritiesProfessor = AuthorityUtils.createAuthorityList("ROLE_USUARIO", "ROLE_PROFESSOR");
		List<GrantedAuthority> authoritiesUser = AuthorityUtils.createAuthorityList("ROLE_USUARIO");
		
		List<GrantedAuthority> authoritiesAtual;
		switch (user.getFuncao().toString()) {
		case "ADMINISTRADOR":
			authoritiesAtual = authoritiesAdmin;
			break;
		case "PROFESSOR":
			authoritiesAtual = authoritiesProfessor;
			break;
		default:
			authoritiesAtual = authoritiesUser;
			break;
		}
		return new User(user.getNome(), user.getSenha(), authoritiesAtual);
	}
}
