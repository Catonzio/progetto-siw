package it.uniroma3.siw.progettosiw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
@Entity
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Field
	private String nome;
	@Column
	private LocalDate dataCreazione;
	@ManyToOne
	private Fotografo autore;
	@OneToMany(mappedBy = "album")
	private List<Foto> foto;
//	@Column
//	private Foto fotoCopertina;

	public Album() {

	}

	public Album(String nome, Fotografo autore) {
		this.nome = nome;
		dataCreazione = LocalDate.now();
		this.autore = autore;
		foto = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData() {
		return dataCreazione;
	}

	public void setData(LocalDate data) {
		dataCreazione = data;
	}

	public List<Foto> getFoto() {
		return foto;
	}

	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}

	public Fotografo getAutore() {
		return autore;
	}

	public void setAutore(Fotografo autore) {
		this.autore = autore;
	}

//	public Foto getFotoCopertina() {
//		return fotoCopertina;
//	}
//
//	public void setFotoCopertina(Foto fotoCopertina) {
//		this.fotoCopertina = fotoCopertina;
//	}

	public void aggiungiFoto(Foto foto) {
		if (foto != null) {
			this.foto.add(foto);
		}
	}

}
