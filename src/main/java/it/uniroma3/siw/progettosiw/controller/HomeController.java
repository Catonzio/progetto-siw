package it.uniroma3.siw.progettosiw.controller;

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
import it.uniroma3.siw.progettosiw.service.FotoService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.service.HibernateSearchService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
public class HomeController {

	@Autowired
	private FotoService fotoService;

	@Autowired
	private FotografoService fotografoService;

	@Autowired
	private HibernateSearchService hibernateSearchService;

	private VerifyLogIn verify = new VerifyLogIn();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inizia(Model model) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografie", fotoService.tutteFoto());
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		return "paginaIniziale.html";
	}

	@GetMapping(value = "/ricerca")
	public String ricerca(Model model, @RequestParam("ricerca") String ricerca) {
		verify.addLoginAttributes(model);
		List<Fotografo> fotografi = trovaFotografi(ricerca);
		List<Album> albumi = trovaAlbum(ricerca);
		List<Foto> fotografie = trovaFoto(ricerca);
		if (!(fotografi.size() == 0)) {
			model.addAttribute("fotografi", fotografi);
		}
		if (!(albumi.size() == 0)) {
			model.addAttribute("albumi", albumi);
		}
		if (!(fotografie.size() == 0)) {
			model.addAttribute("fotografie", fotografie);
		}
		return "risultatiRicerca.html";
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
