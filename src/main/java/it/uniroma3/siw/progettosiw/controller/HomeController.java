package it.uniroma3.siw.progettosiw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progettosiw.model.Album;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.AlbumService;
import it.uniroma3.siw.progettosiw.service.FotoService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.Ricerca;

@Controller
public class HomeController {

	@Autowired
	private FotoService fotoService;

	@Autowired
	private FotografoService fotografoService;

	@Autowired
	private AlbumService albumService;

	@RequestMapping("/")
	public String inizia(Model model) {
		model.addAttribute("fotografie", fotoService.tutteFoto());
		model.addAttribute("ricerca", new Ricerca());
		List<Fotografo> fotografi = fotografoService.tuttiFotografi();
		model.addAttribute("fotografi", fotografi);
		for (Fotografo f : fotografi) {
			StringBuilder sb = new StringBuilder("albumi");
			// sb.append(f.getNome());
			model.addAttribute(sb.toString(), albumService.trovaPerAutore(f));
		}
		return "paginaIniziale.html";
	}

	@PostMapping("/ricerca")
	public String ricerca(Model model, @RequestParam("ricerca") String ricerca) {
		List<Fotografo> fotografi = fotografoService.trovaPerNome(ricerca);
		List<Album> albumi = albumService.trovaPerNome(ricerca);
		List<Foto> fotografie = fotoService.trovaPerSimilarNome(ricerca);
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

//	@RequestMapping(value = "/ricerca", method = RequestMethod.POST)
//	public String ricerca(@Valid @ModelAttribute("ricerca") Ricerca ricerca, Model model, BindingResult bindingResult) {
//		String stringa = ricerca.getStringa1().toString();
//		List<Fotografo> fotografi = fotografoService.trovaPerNome(stringa);
//		List<Album> albumi = albumService.trovaPerNome(stringa);
//		List<Foto> fotografie = fotoService.trovaPerSimilarNome(stringa);
//		if (!(fotografi.size() == 0)) {
//			model.addAttribute("fotografi", fotografi);
//		}
//		if (!(albumi.size() == 0)) {
//			model.addAttribute("albumi", albumi);
//		}
//		if (!(fotografie.size() == 0)) {
//			model.addAttribute("fotografie", fotografie);
//		}
//		return "risultatiRicerca.html";
//	}
}
