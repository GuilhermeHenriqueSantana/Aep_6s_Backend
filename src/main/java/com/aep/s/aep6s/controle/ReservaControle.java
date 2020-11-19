package com.aep.s.aep6s.controle;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.aep.s.aep6s.modelos.Professor;
import com.aep.s.aep6s.modelos.Reserva;
import com.aep.s.aep6s.modelos.Turma;
import com.aep.s.aep6s.repositorio.HorarioRepositorio;
import com.aep.s.aep6s.repositorio.ProfessorRepositorio;
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
	
	@Autowired
	ProfessorRepositorio professorRepositorio;

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
	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('PROFESSOR')")
	public ResponseEntity<ReservaDto> cadastrar(@RequestBody @Valid ReservaForm form, UriComponentsBuilder uriBuilder, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		defineSePodeManipularReserva(form.getTurmaId(), userDetails);
		
		Reserva reserva = form.converter(horarioRepositorio, turmaRepositorio, professorRepositorio);
		reservaRepositorio.save(reserva);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(reserva.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReservaDto(reserva));				
	}

	private void defineSePodeManipularReserva(Long turmaId, UserDetails userDetails) throws Exception {
		boolean admin = false;	
		for (GrantedAuthority x : userDetails.getAuthorities()) {
			if (x.toString().equals("ROLE_ADMINISTRADOR")) {
				admin = true;
			}
		}
		
		if (!admin) {
			boolean turmaEncontrada = false;
			Professor professor = professorRepositorio.findByUsuarioUserName(userDetails.getUsername()).get();
			for (Turma turma : professor.getTurmas()) {
				if (turma.getId() == turmaId) {
					turmaEncontrada = true;
				}
			}
			if (!turmaEncontrada) {
				throw new Exception("Professor não da aula para a turma de id:" + turmaId);
			}
		}
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
	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('PROFESSOR')")
	public ResponseEntity<ReservaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoReservaForm form, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
		defineSePodeManipularReserva(form.getTurmaId(), userDetails);
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
	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('PROFESSOR')")
	public ResponseEntity<?> remover(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) throws Exception{
		Optional<Reserva> reservaOpt = reservaRepositorio.findById(id);
		if(reservaOpt.isPresent()) {
			Reserva reserva = reservaOpt.get();
			defineSePodeManipularReserva(reserva.getTurma().getId(), userDetails);
			Horario horario = horarioRepositorio.findById(reserva.getHorario().getId()).get();
			Turma turma = turmaRepositorio.findById(reserva.getTurma().getId()).get();
			horario.getReservas().remove(reserva);
			turma.getReservas().remove(reserva);
			reservaRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
