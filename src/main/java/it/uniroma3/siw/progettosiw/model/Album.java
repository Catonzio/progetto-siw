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

@Entity
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String nome;
	@Column
	private LocalDate dataCreazione;
	@ManyToOne
	private Fotografo autore;
	@OneToMany(mappedBy = "album")
	private List<Foto> foto;

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

	public Fotografo getAutore() {
		return autore;
	}

	public void setAutore(Fotografo autore) {
		this.autore = autore;
	}

	public void aggiungiFoto(Foto foto) {
		if (foto != null) {
			this.foto.add(foto);
		}
	}

}
