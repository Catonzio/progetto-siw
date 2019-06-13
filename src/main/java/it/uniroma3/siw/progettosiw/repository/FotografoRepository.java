package it.uniroma3.siw.progettosiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettosiw.model.Fotografo;

public interface FotografoRepository extends CrudRepository<Fotografo, Long> {

	public List<Fotografo> findByNome(String nome);
}
