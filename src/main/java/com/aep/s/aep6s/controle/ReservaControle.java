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

import com.aep.s.aep6s.controle.dto.ReservaDto;
import com.aep.s.aep6s.controle.form.AtualizacaoReservaForm;
import com.aep.s.aep6s.controle.form.ReservaForm;
import com.aep.s.aep6s.modelos.Horario;
import com.aep.s.aep6s.modelos.Reserva;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.ReservaRepositorio;
import com.aep.s.aep6s.repositorio.TurmaRepositorio;

@RestController
@RequestMapping("/reservas")
public class ReservaControle {
	
	@Autowired
	ReservaRepositorio reservaRepositorio;
	
	@Autowired
	HorarioRepositorio horarioRepositorio;
	
	@Autowired
	TurmaRepositorio turmaRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<ReservaDto> lista() {
		
		List<Reserva> reservas = reservaRepositorio.findAll();
		
		return ReservaDto.converter(reservas);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<ReservaDto> cadastrar(@RequestBody @Valid ReservaForm form, UriComponentsBuilder uriBuilder) throws Exception {
		Reserva reserva = form.converter(horarioRepositorio, turmaRepositorio);
		reservaRepositorio.save(reserva);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(reserva.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReservaDto(reserva));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<ReservaDto> detalhar(@PathVariable Long id) {
		Optional<Reserva> reservaOpt = reservaRepositorio.findById(id);
		if(reservaOpt.isPresent()) {
			return ResponseEntity.ok(new ReservaDto(reservaOpt.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<ReservaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoReservaForm form) throws Exception{
		Optional<Reserva> opcional = reservaRepositorio.findById(id);
		if(opcional.isPresent()) {
			Reserva reserva = form.atualiza(id, horarioRepositorio, turmaRepositorio, reservaRepositorio);
			return ResponseEntity.ok(new ReservaDto(reserva));
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Reserva> reservaOpt = reservaRepositorio.findById(id);
		if(reservaOpt.isPresent()) {
			Reserva reserva = reservaOpt.get();
			Horario horario = horarioRepositorio.findById(reserva.getHorario().getId()).get();
			Turma turma = turmaRepositorio.findById(reserva.getHorario().getId()).get();
			horario.getReservas().remove(reserva);
			turma.getReservas().remove(reserva);
			reservaRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
