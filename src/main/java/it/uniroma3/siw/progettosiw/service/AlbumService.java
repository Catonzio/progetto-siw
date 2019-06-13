package it.uniroma3.siw.progettosiw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.repository.AlbumRepository;

@Service
public class AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Transactional
	public void save(Album album) {
		albumRepository.save(album);
	}

	@Transactional
	public List<Album> tuttiAlbum() {
		return (List<Album>) albumRepository.findAll();
	}

	@Transactional
	public List<Album> trovaPerNome(String nome) {
		return albumRepository.findByNome(nome);
	}

	@Transactional
	public List<Album> trovaPerAutore(Fotografo autore) {
		return albumRepository.findByAutore(autore);
	}

}
