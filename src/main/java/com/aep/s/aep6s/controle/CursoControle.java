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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aep.s.aep6s.controle.dto.CursoDto;
import com.aep.s.aep6s.controle.form.AtualizacaoCursoForm;
import com.aep.s.aep6s.controle.form.CursoForm;
import com.aep.s.aep6s.modelos.Curso;
import com.aep.s.aep6s.repositorio.CursoRepositorio;

@RestController
@RequestMapping("/cursos")
public class CursoControle {
	
	@Autowired
	CursoRepositorio cursoRepositorio;
	
	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<CursoDto> lista() {
		
		List<Curso> cursos = new ArrayList<>();
		
		cursos = cursoRepositorio.findAll();
		
		return CursoDto.converter(cursos);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<CursoDto> cadastrar(@RequestBody @Valid CursoForm form, UriComponentsBuilder uriBuilder) {
		Curso curso = form.converter();
		cursoRepositorio.save(curso);
		
		URI uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(uri).body(new CursoDto(curso));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<CursoDto> detalhar(@PathVariable Long id) {
		Optional<Curso> cursoOpt = cursoRepositorio.findById(id);
		if(cursoOpt.isPresent()) {
			return ResponseEntity.ok(new CursoDto(cursoOpt.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<CursoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoCursoForm form) throws Exception{
		Optional<Curso> opcional = cursoRepositorio.findById(id);
		if(opcional.isPresent()) {
			Curso curso = form.atualiza(id, cursoRepositorio);
			return ResponseEntity.ok(new CursoDto(curso));
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Curso> cursoOpt = cursoRepositorio.findById(id);
		if(cursoOpt.isPresent()) {
			cursoRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
