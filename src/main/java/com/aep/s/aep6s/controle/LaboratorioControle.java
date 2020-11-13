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

import com.aep.s.aep6s.controle.dto.LaboratorioDto;
import com.aep.s.aep6s.controle.form.AtualizacaoLaboratorioForm;
import com.aep.s.aep6s.controle.form.LaboratorioForm;
import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.modelos.Laboratorio;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioControle {
	
	@Autowired
	LaboratorioRepositorio laboratorioRepositorio;
	
	@Autowired
	BlocoRepositorio blocoRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<LaboratorioDto> lista(@RequestParam(required = false) String nome) {
		
		List<Laboratorio> laboratorios = new ArrayList<>();
		
		if (nome == null) {
			laboratorios = laboratorioRepositorio.findAll();
		} else {
			Optional<List<Laboratorio>> laboratoriosOpt = laboratorioRepositorio.findByNome(nome);
			if(laboratoriosOpt.isPresent()) {
				laboratorios = laboratoriosOpt.get();
			}
		}
		
		return LaboratorioDto.converter(laboratorios);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<LaboratorioDto> cadastrar(@RequestBody @Valid LaboratorioForm form, UriComponentsBuilder uriBuilder) throws Exception {
		Laboratorio laboratorio = form.converter(blocoRepositorio);
		laboratorioRepositorio.save(laboratorio);
		
		URI uri = uriBuilder.path("/laboratorios/{id}").buildAndExpand(laboratorio.getId()).toUri();
		return ResponseEntity.created(uri).body(new LaboratorioDto(laboratorio));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<LaboratorioDto> detalhar(@PathVariable Long id) {
		Optional<Laboratorio> laboratorioOpt = laboratorioRepositorio.findById(id);
		if(laboratorioOpt.isPresent()) {
			return ResponseEntity.ok(new LaboratorioDto(laboratorioOpt.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<LaboratorioDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoLaboratorioForm form) throws Exception{
		Optional<Laboratorio> opcional = laboratorioRepositorio.findById(id);
		if(opcional.isPresent()) {
			Laboratorio laboratorio = form.atualiza(id, blocoRepositorio, laboratorioRepositorio);
			return ResponseEntity.ok(new LaboratorioDto(laboratorio));
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Laboratorio> laboratorioOpt = laboratorioRepositorio.findById(id);
		if(laboratorioOpt.isPresent()) {
			Bloco bloco = blocoRepositorio.getOne(laboratorioOpt.get().getBloco().getId());
			bloco.getLaboratorios().remove(laboratorioOpt.get());
			laboratorioRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
