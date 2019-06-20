package it.uniroma3.siw.progettosiw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "nome", "cognome" }))
public class Fotografo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	@Field
	private String nome;
	@Column
	private String cognome;
	@OneToMany(mappedBy = "autore")
	private List<Album> album;
	@OneToMany(mappedBy = "autore")
	private List<Foto> fotoFatte;

	public Fotografo() {
		album = new ArrayList<>();
		fotoFatte = new ArrayList<>();
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

	public List<Album> getAlbum() {
		return album;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCognome() {
		return cognome;
	}

	public void aggiungiAlbum(Album album) {
		if (album != null) {
			this.album.add(album);
		}
	}

	public void aggiungiFoto(Foto foto) {
		if (foto != null) {
			fotoFatte.add(foto);
		}
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		Fotografo that = (Fotografo) obj;
		return getNome().equals(that.getNome()) && getCognome().equals(that.getCognome());
	}
}
