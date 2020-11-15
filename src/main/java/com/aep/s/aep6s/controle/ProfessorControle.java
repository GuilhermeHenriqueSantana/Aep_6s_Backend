package com.aep.s.aep6s.controle;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aep.s.aep6s.controle.dto.ProfessorDto;
import com.aep.s.aep6s.controle.form.AtualizacaoProfessorForm;
import com.aep.s.aep6s.controle.form.ProfessorForm;
import com.aep.s.aep6s.modelos.Professor;
import com.aep.s.aep6s.repositorio.ProfessorRepositorio;
import com.aep.s.aep6s.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/professores")
public class ProfessorControle {
	
	@Autowired
	ProfessorRepositorio professorRepositorio;
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<ProfessorDto> lista() {
		
		List<Professor> professores = professorRepositorio.findAll();
		
		return ProfessorDto.converter(professores);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<ProfessorDto> cadastrar(@RequestBody @Valid ProfessorForm form, UriComponentsBuilder uriBuilder) throws Exception {
		Professor professor = form.converter(usuarioRepositorio);
		professorRepositorio.save(professor);
		
		URI uri = uriBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProfessorDto(professor));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<ProfessorDto> detalhar(@PathVariable Long id) {
		Optional<Professor> professor = professorRepositorio.findById(id);
		if(professor.isPresent()) {
			return ResponseEntity.ok(new ProfessorDto(professor.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<ProfessorDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoProfessorForm form){
		Optional<Professor> opcional = professorRepositorio.findById(id);
		if(opcional.isPresent()) {
			Professor professor = form.atualiza(id, professorRepositorio);
			return ResponseEntity.ok(new ProfessorDto(professor));
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Professor> professor = professorRepositorio.findById(id);
		if(professor.isPresent()) {
			professorRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
