package it.uniroma3.siw.progettosiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.model.Fotografo;

public interface AlbumRepository extends CrudRepository<Album, Long> {

	public List<Album> findByNome(String nome);

	public List<Album> findByAutore(Fotografo autore);

}
