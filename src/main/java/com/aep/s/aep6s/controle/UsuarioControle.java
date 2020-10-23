package com.aep.s.aep6s.controle;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aep.s.aep6s.controle.dto.UsuarioDto;
import com.aep.s.aep6s.controle.form.UsuarioForm;
import com.aep.s.aep6s.modelos.Usuario;
import com.aep.s.aep6s.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@GetMapping
	public List<UsuarioDto> lista(@RequestParam(required = false) String userName) {
		
		List<Usuario> usuarios = new ArrayList<>();
		
		if (userName == null) {
			usuarios = usuarioRepositorio.findAll();
		} else {
			Optional<Usuario> usuarioOptional = usuarioRepositorio.findByUserName(userName);
			if(usuarioOptional.isPresent()) {
				usuarios.add(usuarioOptional.get());
			}
		}
		
		return UsuarioDto.converter(usuarios);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
		Usuario usuario = form.converter();
		usuarioRepositorio.save(usuario);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
}
