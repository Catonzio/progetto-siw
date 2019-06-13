package it.uniroma3.siw.progettosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.AlbumService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.Ricerca;
import it.uniroma3.siw.progettosiw.validator.RicercaValidator;

@Controller
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@Autowired
	private FotografoService fotografoService;

	@Autowired
	private RicercaValidator ricercaValidator;

	@RequestMapping("/album")
	public String album(Model model) {
		model.addAttribute("albumi", albumService.tuttiAlbum());
		model.addAttribute("ricerca", new Ricerca());
		return "album.html";
	}

	@RequestMapping("/addAlbum")
	public String addAlbum(Model model) {
		model.addAttribute("ricerca", new Ricerca());
		return "addAlbum.html";
	}

	@RequestMapping(value = "/aggiungiAlbum", method = RequestMethod.POST)
	public String aggiungiAlbum(@ModelAttribute("ricerca") Ricerca ricerca, Model model, BindingResult bindingResult) {
		ricercaValidator.validate(ricerca, bindingResult);
		if (!bindingResult.hasErrors()) {
			Fotografo fotografo = fotografoService.trovaPerNome(ricerca.getStringa2()).get(0);
			Album album = new Album(ricerca.getStringa1(), fotografo);
			album.setAutore(fotografo);
			fotografo.aggiungiAlbum(album);
			albumService.save(album);
			model.addAttribute("albumi", albumService.tuttiAlbum());
			return "album.html";
		} else
			return "addAlbum.html";
	}

	@PostMapping("/altriNomi")
	public String altriNomi(Model model, @RequestParam("nome") String nome,
			@RequestParam("altroNome") String altroNome) {
		model.addAttribute("nome", nome);
		model.addAttribute("altroNome", altroNome);
		return "prova.html";
	}

//	@RequestMapping(value = "/aggiungiAutore", method = RequestMethod.POST)
//	public String aggiungiAutore(@ModelAttribute("fotografo") Fotografo fotografo, Model model) {
//		return "album.html";
//	}
}
