package it.uniroma3.siw.progettosiw.model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {

	private List<Foto> fotoCarrello;

	public Carrello() {
		fotoCarrello = new ArrayList<>();
	}

	public List<Foto> getFotoCarrello() {
		return fotoCarrello;
	}

	public void setFotoCarrello(List<Foto> foto) {
		fotoCarrello = foto;
	}

	public void addFoto(Foto foto) {
		if (foto != null) {
			fotoCarrello.add(foto);
		}
	}

}
