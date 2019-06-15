package it.uniroma3.siw.progettosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.AlbumService;
import it.uniroma3.siw.progettosiw.service.FotografoService;

@Controller
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@Autowired
	private FotografoService fotografoService;

//	@Autowired
//	private RicercaValidator ricercaValidator;

	@RequestMapping("/album")
	public String album(Model model) {
		model.addAttribute("albumi", albumService.tuttiAlbum());
		return "album.html";
	}

	@RequestMapping("/addAlbum")
	public String addAlbum(Model model) {
		return "addAlbum.html";
	}

	@RequestMapping(value = "/album/{idF}/{idA}", method = RequestMethod.POST)
	public String selezionaAlbum(Model model, @RequestParam("idF") Long idF, @RequestParam("idA") Long idA) {
		return "uploadFoto.html";
	}

//	@RequestMapping(value = "/aggiungiAlbum", method = RequestMethod.POST)
//	public String aggiungiAlbum(@ModelAttribute("ricerca") Ricerca ricerca, Model model, BindingResult bindingResult) {
//		ricercaValidator.validate(ricerca, bindingResult);
//		if (!bindingResult.hasErrors()) {
//			Fotografo fotografo = fotografoService.trovaPerNome(ricerca.getStringa2()).get(0);
//			Album album = new Album(ricerca.getStringa1(), fotografo);
//			fotografo.aggiungiAlbum(album);
//			albumService.save(album);
//			model.addAttribute("albumi", albumService.tuttiAlbum());
//			return "album.html";
//		} else
//			return "addAlbum.html";
//	}

	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public String visualizzaAlbum(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			Album album = albumService.trovaPerId(id);
			Fotografo fotografo = album.getAutore();
			model.addAttribute("album", album);
			if (fotografo != null) {
				model.addAttribute("fotografo", fotografo);
			}
			return "paginaAlbum.html";
		} else {
			model.addAttribute("albumi", albumService.tuttiAlbum());
			return "album.html";
		}
	}

	@PostMapping("/aggiungiAlbum")
	public String altriNomi(Model model, @RequestParam("nomeAlbum") String nomeAlbum,
			@RequestParam("nomeFotografo") String nomeFotografo) {
		if (!(nomeFotografo.isBlank()) && !(nomeAlbum.isBlank())) {
			Fotografo fotografo = fotografoService.trovaPerNome(nomeFotografo).get(0);
			Album album = new Album(nomeAlbum, fotografo);
			fotografo.aggiungiAlbum(album);
			albumService.save(album);
			model.addAttribute("albumi", albumService.tuttiAlbum());
			return "album.html";
		} else
			return "addAlbum.html";
	}

//	@RequestMapping(value = "/aggiungiAutore", method = RequestMethod.POST)
//	public String aggiungiAutore(@ModelAttribute("fotografo") Fotografo fotografo, Model model) {
//		return "album.html";
//	}
}
