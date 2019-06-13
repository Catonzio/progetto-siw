package it.uniroma3.siw.progettosiw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettosiw.model.Foto;

public interface FotoRepository extends CrudRepository<Foto, Long>, JpaRepository<Foto, Long> {
	public Foto findFirstByOrderByIdDesc();

	public List<Foto> findByFileName(String stringa);

	@Query("select f from Foto f where f.fileName like ?1")
	public List<Foto> findBySimilarName(String stringa);
}
