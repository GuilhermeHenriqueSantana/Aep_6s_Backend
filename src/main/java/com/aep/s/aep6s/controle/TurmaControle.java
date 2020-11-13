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


import com.aep.s.aep6s.controle.dto.TurmaDto;
import com.aep.s.aep6s.controle.form.AtualizacaoTurmaForm;
import com.aep.s.aep6s.controle.form.TurmaForm;
import com.aep.s.aep6s.modelos.Curso;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.CursoRepositorio;
import com.aep.s.aep6s.repositorio.TurmaRepositorio;

@RestController
@RequestMapping("/turmas")
public class TurmaControle {
	
	@Autowired
	TurmaRepositorio turmaRepositorio;
	
	@Autowired
	CursoRepositorio cursoRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<TurmaDto> lista() {
		
		List<Turma> turmas = turmaRepositorio.findAll();
		
		return TurmaDto.converter(turmas);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<TurmaDto> cadastrar(@RequestBody @Valid TurmaForm form, UriComponentsBuilder uriBuilder) throws Exception {
		Turma turma = form.converter(cursoRepositorio);
		turmaRepositorio.save(turma);
		
		URI uri = uriBuilder.path("/turmas/{id}").buildAndExpand(turma.getId()).toUri();
		return ResponseEntity.created(uri).body(new TurmaDto(turma));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<TurmaDto> detalhar(@PathVariable Long id) {
		Optional<Turma> turma = turmaRepositorio.findById(id);
		if(turma.isPresent()) {
			return ResponseEntity.ok(new TurmaDto(turma.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<TurmaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTurmaForm form) throws Exception{
		Optional<Turma> opcional = turmaRepositorio.findById(id);
		if(opcional.isPresent()) {
			Turma turma = form.atualiza(id, turmaRepositorio,cursoRepositorio);
			return ResponseEntity.ok(new TurmaDto(turma));
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Turma> turma = turmaRepositorio.findById(id);
		if(turma.isPresent()) {
			Curso curso = cursoRepositorio.getOne(turma.get().getCurso().getId());
			curso.getTurmas().remove(turma.get());
			turmaRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
