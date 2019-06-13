package it.uniroma3.siw.progettosiw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettosiw.model.Funzionario;

public interface FunzionarioRepository extends CrudRepository<Funzionario, Long> {

	public Funzionario findByNome(String nome);

	public Funzionario findByNomeAndPassword(String nome, String password);
}
