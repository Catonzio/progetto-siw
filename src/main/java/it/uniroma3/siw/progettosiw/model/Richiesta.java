package it.uniroma3.siw.progettosiw.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Richiesta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String nomeRichiedente;

	@Column
	private String cognomeRichiedente;

	@Column
	private String emailRichiedente;

	@Column
	private String testoRichiesta;

	@ManyToMany
	@JoinTable(name = "richiesta_foto", joinColumns = @JoinColumn(name = "richiesta_id"), inverseJoinColumns = @JoinColumn(name = "foto_id"))
	private List<Foto> fotoRichieste;

	public Richiesta() {

	}

	public Richiesta(String nome, String cognome, String email, String motivo) {
		nomeRichiedente = nome;
		cognomeRichiedente = cognome;
		emailRichiedente = email;
		testoRichiesta = motivo;
	}

	public String getNomeRichiedente() {
		return nomeRichiedente;
	}

	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}

	public String getEmailRichiedente() {
		return emailRichiedente;
	}

	public void setEmailRichiedente(String emailRichiedente) {
		this.emailRichiedente = emailRichiedente;
	}

	public String getTestoRichiesta() {
		return testoRichiesta;
	}

	public void setTestoRichiesta(String testoRichiesta) {
		this.testoRichiesta = testoRichiesta;
	}

	public List<Foto> getFotoRichieste() {
		return fotoRichieste;
	}

	public void setFotoRichieste(List<Foto> fotoRichieste) {
		this.fotoRichieste = fotoRichieste;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}

	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}

}
