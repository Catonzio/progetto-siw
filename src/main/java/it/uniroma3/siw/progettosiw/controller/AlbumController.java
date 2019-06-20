package it.uniroma3.siw.progettosiw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.AlbumService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@Autowired
	private FotografoService fotografoService;

	private VerifyLogIn verify = new VerifyLogIn();

	@ModelAttribute
	public void addCommonAttribute(Model model) {
		List<Album> albums = albumService.tuttiAlbum();
		if ((albums != null) && !albums.isEmpty()) {
			model.addAttribute("albumi", albums);
			model.addAttribute("albumSelezionato", albums.get(0));
		}
	}

	@RequestMapping("/album")
	public String album(Model model) {
		verify.addLoginAttributes(model);
		List<Album> albums = albumService.tuttiAlbum();
		model.addAttribute("albumSelezionato", albums.get(0));
		return "album.html";
	}

	@RequestMapping("/addAlbum")
	public String addAlbum(Model model) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		return "addAlbum.html";
	}

	@RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
	public String visualizzaAlbum(@PathVariable("id") Long id, Model model) {
		verify.addLoginAttributes(model);
		if (id != null) {
			Album album = albumService.trovaPerId(id);
			Fotografo fotografo = album.getAutore();
			model.addAttribute("albumSelezionato", album);
			if (fotografo != null) {
				model.addAttribute("fotografo", fotografo);
			}
			return "album.html";
		} else {
			model.addAttribute("albumi", albumService.tuttiAlbum());
			return "album.html";
		}
	}

	@GetMapping("/aggiungiAlbum")
	public String altriNomi(Model model, @RequestParam("nomeAlbum") String nomeAlbum,
			@RequestParam("nomeFotografo") String nomeFotografo) {
		verify.addLoginAttributes(model);
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
