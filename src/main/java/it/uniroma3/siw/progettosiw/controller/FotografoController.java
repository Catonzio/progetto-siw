package it.uniroma3.siw.progettosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.Ricerca;
import it.uniroma3.siw.progettosiw.validator.FotografoValidator;

@Controller
public class FotografoController {

	@Autowired
	private FotografoService fotografoService;

	@Autowired
	private FotografoValidator fotografoValidator;

	@RequestMapping(value = "/fotografi")
	public String fotografi(Model model) {
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
		model.addAttribute("ricerca", new Ricerca());
		return "fotografi.html";
	}

	@RequestMapping("/addFotografo")
	public String addFotografo(Model model) {
		model.addAttribute("fotografo", new Fotografo());
		model.addAttribute("ricerca", new Ricerca());
		return "addFotografo.html";
	}

	@RequestMapping(value = "/aggiungiFotografo", method = RequestMethod.POST)
	public String aggiungiFotografo(@ModelAttribute("fotografo") Fotografo fotografo, Model model,
			BindingResult bindingResult) {
		model.addAttribute("ricerca", new Ricerca());
		fotografoValidator.validate(fotografo, bindingResult);
		if (!bindingResult.hasErrors()) {
			model.addAttribute("ricerca", new Ricerca());
			fotografoService.save(fotografo);
			model.addAttribute("fotografi", fotografoService.tuttiFotografi());
			return "fotografi.html";
		} else
			return "addFotografo.html";
	}
}
