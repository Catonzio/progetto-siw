package it.uniroma3.siw.progettosiw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progettosiw.model.Richiesta;
import it.uniroma3.siw.progettosiw.repository.RichiestaRepository;

@Service
public class RichiestaService {

	@Autowired
	private RichiestaRepository richiestaRepository;

	@Transactional
	public List<Richiesta> tutteRichieste() {
		return (List<Richiesta>) richiestaRepository.findAll();
	}

	@Transactional
	public void save(Richiesta richiesta) {
		richiestaRepository.save(richiesta);
	}

	@Transactional
	public Richiesta trovaPerId(Long id) {
		return richiestaRepository.findById(id).get();
	}
}
