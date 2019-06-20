package it.uniroma3.siw.progettosiw.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.model.Funzionario;
import it.uniroma3.siw.progettosiw.service.AlbumService;
import it.uniroma3.siw.progettosiw.service.FotoService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.service.FunzionarioService;
import it.uniroma3.siw.progettosiw.service.HibernateSearchService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
public class HomeController {

	static boolean isAlreadyInitialized = false;

	@Autowired
	private FotoService fotoService;

	@Autowired
	private FotografoService fotografoService;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private FunzionarioService funzionarioService;

	@Autowired
	private HibernateSearchService hibernateSearchService;

	private VerifyLogIn verify = new VerifyLogIn();

	public void inizializzaDatabase() {
		for (int i = 1; i < 6; i++) {
			Fotografo f = new Fotografo();
			f.setNome("Fotografo" + i);
			f.setCognome("Fotografo" + i);
			fotografoService.save(f);
			for (int j = 1; j < 3; j++) {
				Album a = new Album();
				a.setNome("Album" + i + "." + j);
				a.setAutore(f);
				a.setData(LocalDate.now());
				f.aggiungiAlbum(a);
				albumService.save(a);
			}
		}
		for (int i = 1; i < 3; i++) {
			Funzionario f = new Funzionario();
			f.setNome("Funzionario" + i);
			f.setPassword("password" + i);
			f.setRole("ADMIN");
			funzionarioService.save(f);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inizia(Model model) {
		verify.addLoginAttributes(model);
		if (!isAlreadyInitialized) {
			inizializzaDatabase();
			isAlreadyInitialized = true;
		}
		model.addAttribute("fotografie", fotoService.tutteFoto());
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		return "paginaIniziale.html";
	}

	@GetMapping(value = "/ricerca")
	public String ricerca(Model model, @RequestParam("ricerca") String ricerca) {
		verify.addLoginAttributes(model);
		String paginaDaTornare = "paginaIniziale.html";

		List<Fotografo> fotografi = trovaFotografi(ricerca);
		List<Album> albumi = trovaAlbum(ricerca);
		List<Foto> fotografie = trovaFoto(ricerca);
		if ((fotografi != null) && (albumi != null) && (fotografie != null)) {
			if (!(fotografi.size() == 0)) {
				model.addAttribute("fotografi", fotografi);
			}
			if (!(albumi.size() == 0)) {
				model.addAttribute("albumi", albumi);
			}
			if (!(fotografie.size() == 0)) {
				model.addAttribute("fotografie", fotografie);
			}
			paginaDaTornare = "risultatiRicerca.html";
		} else {
			model.addAttribute("fotografie", fotoService.tutteFoto());
			model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		}
		return paginaDaTornare;
	}

	public List<Fotografo> trovaFotografi(String q) {
		List<Fotografo> searchResults = null;
		try {
			if (!q.isBlank()) {
				searchResults = hibernateSearchService.fuzzySearchFotografi(q);
				System.out.println("Risultati ricerca: " + searchResults.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchResults;
	}

	public List<Foto> trovaFoto(String q) {
		List<Foto> searchResults = null;
		try {
			if (!q.isBlank()) {
				searchResults = hibernateSearchService.fuzzySearchFotos(q);
				System.out.println("Risultati ricerca: " + searchResults.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchResults;
	}

	public List<Album> trovaAlbum(String q) {
		List<Album> searchResults = null;
		try {
			if (!q.isBlank()) {
				searchResults = hibernateSearchService.fuzzySearchAlbums(q);
				System.out.println("Risultati ricerca: " + searchResults.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchResults;
	}
}
