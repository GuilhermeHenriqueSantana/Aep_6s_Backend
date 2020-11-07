package com.aep.s.aep6s.controle;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aep.s.aep6s.controle.dto.UsuarioDto;
import com.aep.s.aep6s.controle.form.AtualizacaoUsuarioForm;
import com.aep.s.aep6s.controle.form.UsuarioForm;
import com.aep.s.aep6s.modelos.Usuario;
import com.aep.s.aep6s.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRADOR')")
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
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
		Usuario usuario = form.converter();
		usuarioRepositorio.save(usuario);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<UsuarioDto> detalhar(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepositorio.findById(id);
		if(usuario.isPresent()) {
			return ResponseEntity.ok(new UsuarioDto(usuario.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoUsuarioForm form){
		Optional<Usuario> opcional = usuarioRepositorio.findById(id);
		if(opcional.isPresent()) {
			Usuario usuario = form.atualiza(id, usuarioRepositorio);
			return ResponseEntity.ok(new UsuarioDto(usuario));
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Usuario> usuario = usuarioRepositorio.findById(id);
		if(usuario.isPresent()) {
			usuarioRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
