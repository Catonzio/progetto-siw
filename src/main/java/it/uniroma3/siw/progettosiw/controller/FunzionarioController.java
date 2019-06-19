package it.uniroma3.siw.progettosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.uniroma3.siw.progettosiw.service.FotoService;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
public class FunzionarioController {

	@Autowired
	private FotoService fotoService;

	@Autowired
	private FotografoService fotografoService;

	private VerifyLogIn verify = new VerifyLogIn();

//	@RequestMapping(value = "/funzionario", method = RequestMethod.GET)
//	public String inizialeFunzionario(Model model) {
//		model.addAttribute("fotografie", fotoService.tutteFoto());
//		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
//		verify.addLoginAttributes(model);
//		return "paginaIniziale.html";
//	}
//
//	@RequestMapping(value = "/funzionario/fotografi")
//	public String fotografi(Model model) {
//		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
//		verify.addLoginAttributes(model);
//		return "fotografi.html";
//	}

}
