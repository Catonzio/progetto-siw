package it.uniroma3.siw.progettosiw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progettosiw.model.Funzionario;
import it.uniroma3.siw.progettosiw.repository.FunzionarioRepository;

@Service
public class FunzionarioService {

	@Autowired
	private FunzionarioRepository funzionarioRepository;
	
	@Transactional
	public Funzionario inserisciFunzionario(Funzionario funzionario) {
		return funzionarioRepository.save(funzionario);
	}
	
	@Transactional
	public List<Funzionario> tutti() {
		return (List<Funzionario>) funzionarioRepository.findAll();
	}
	
	@Transactional
	public Funzionario funzionarioPerId(Long id) {
		return funzionarioRepository.findById(id).get();
	}
}
