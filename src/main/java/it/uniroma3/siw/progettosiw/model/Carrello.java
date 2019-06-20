package it.uniroma3.siw.progettosiw.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
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

	public void svuota() {
		fotoCarrello.clear();
	}

	public void aggiungiFoto(Foto foto) {
		if (foto != null) {
			if (!getFotoCarrello().contains(foto)) {
				addFoto(foto);
				System.out.println("Carrello: " + getFotoCarrello().toString());
			}
		}
	}

	public void deleteFoto(Foto foto) {
		System.out.println("Svuota carrello: foto trovata -> " + foto.toString());
		if (foto != null) {
			System.out.println("Svuota carrello: foto non nulla -> " + foto.toString());
			if (getFotoCarrello().contains(foto)) {
				getFotoCarrello().remove(foto);
				System.out.println("Svuota carrello: foto cancellata -> " + foto.toString());
			}
		}
	}

}
