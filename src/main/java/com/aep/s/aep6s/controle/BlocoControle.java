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

import com.aep.s.aep6s.controle.dto.BlocoDto;
import com.aep.s.aep6s.controle.form.AtualizacaoBlocoForm;
import com.aep.s.aep6s.controle.form.BlocoForm;
import com.aep.s.aep6s.modelos.Bloco;
import com.aep.s.aep6s.repositorio.BlocoRepositorio;
import com.aep.s.aep6s.repositorio.LaboratorioRepositorio;

@RestController
@RequestMapping("/blocos")
public class BlocoControle {
	
	@Autowired
	BlocoRepositorio blocoRepositorio;
	
	@Autowired
	LaboratorioRepositorio laboratorioRepositorio;

	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public List<BlocoDto> lista(@RequestParam(required = false) String nome) {
		
		List<Bloco> blocos = new ArrayList<>();
		
		if (nome == null) {
			blocos = blocoRepositorio.findAll();
		} else {
			Optional<List<Bloco>> blocosOptional = blocoRepositorio.findByNome(nome);
			if(blocosOptional.isPresent()) {
				blocos = blocosOptional.get();
			}
		}
		
		return BlocoDto.converter(blocos);
	}
	
	@CrossOrigin
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<BlocoDto> cadastrar(@RequestBody @Valid BlocoForm form, UriComponentsBuilder uriBuilder) {
		Bloco bloco = form.converter(laboratorioRepositorio);
		blocoRepositorio.save(bloco);
		
		URI uri = uriBuilder.path("/blocos/{id}").buildAndExpand(bloco.getId()).toUri();
		return ResponseEntity.created(uri).body(new BlocoDto(bloco));
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('LIVRE') or hasRole('PROFESSOR') or hasRole('ADIMINISTRADOR')")
	public ResponseEntity<BlocoDto> detalhar(@PathVariable Long id) {
		Optional<Bloco> bloco = blocoRepositorio.findById(id);
		if(bloco.isPresent()) {
			return ResponseEntity.ok(new BlocoDto(bloco.get()));			
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<BlocoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoBlocoForm form){
		Optional<Bloco> opcional = blocoRepositorio.findById(id);
		if(opcional.isPresent()) {
			Bloco bloco = form.atualiza(id, blocoRepositorio, laboratorioRepositorio);
			return ResponseEntity.ok(new BlocoDto(bloco));
		}
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Bloco> bloco = blocoRepositorio.findById(id);
		if(bloco.isPresent()) {
			blocoRepositorio.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
