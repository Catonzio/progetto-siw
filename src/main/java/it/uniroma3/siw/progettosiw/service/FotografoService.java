package it.uniroma3.siw.progettosiw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.repository.FotografoRepository;

@Service
public class FotografoService {

	@Autowired
	private FotografoRepository fotografoRepository;

	@Transactional
	public void save(Fotografo fotografo) {
		fotografoRepository.save(fotografo);
	}

	@Transactional
	public List<Fotografo> tuttiFotografi() {
		return (List<Fotografo>) fotografoRepository.findAll();
	}

	@Transactional
	public List<Fotografo> trovaPerNome(String nome) {
		return fotografoRepository.findByNome(nome);
	}

	@Transactional
	public Fotografo trovaPerId(Long id) {
		return fotografoRepository.findById(id).get();
	}

}
