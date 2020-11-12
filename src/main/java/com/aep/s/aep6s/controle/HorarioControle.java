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

import com.aep.s.aep6s.controle.dto.HorarioDto;
import com.aep.s.aep6s.controle.form.AtualizacaoHorarioForm;
import com.aep.s.aep6s.controle.form.HorarioForm;
import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;

@RestController
@RequestMapping("/horarios")
public class HorarioControle {
	@Autowired
	HorarioRepositorio horarioRepositorio;
	
	@Autowired
	LaboratorioRepositorio laboratorioRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<HorarioDto> lista() {
		
		List<Horario> horarios = new ArrayList<>();

		horarios = horarioRepositorio.findAll();
	
		return HorarioDto.converter(horarios);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<HorarioDto> cadastrar(@RequestBody @Valid HorarioForm form, UriComponentsBuilder uriBuilder) throws Exception {
		Horario horario = form.converter(laboratorioRepositorio);
		horarioRepositorio.save(horario);
		
		URI uri = uriBuilder.path("/horarios/{id}").buildAndExpand(horario.getId()).toUri();
		return ResponseEntity.created(uri).body(new HorarioDto(horario));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<HorarioDto> detalhar(@PathVariable Long id) {
		Optional<Horario> horario = horarioRepositorio.findById(id);
		if(horario.isPresent()) {
			return ResponseEntity.ok(new HorarioDto(horario.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<HorarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoHorarioForm form) throws Exception{
		Optional<Horario> opcional = horarioRepositorio.findById(id);
		if(opcional.isPresent()) {
			Horario horario = form.atualiza(id, horarioRepositorio, laboratorioRepositorio);
			return ResponseEntity.ok(new HorarioDto(horario));
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Horario> horario = horarioRepositorio.findById(id);
		if(horario.isPresent()) {
			Laboratorio laboratorio = laboratorioRepositorio.getOne(horario.get().getLaboratorio().getId());
			laboratorio.getHorarios().remove(horario.get());
			horarioRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
