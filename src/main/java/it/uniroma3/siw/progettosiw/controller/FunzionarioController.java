package it.uniroma3.siw.progettosiw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progettosiw.model.Funzionario;
import it.uniroma3.siw.progettosiw.service.FunzionarioService;
import it.uniroma3.siw.progettosiw.support.Ricerca;
import it.uniroma3.siw.progettosiw.validator.FunzionarioValidator;

@Controller
public class FunzionarioController {

	@Autowired
	private FunzionarioService funzionarioService;

	@Autowired
	private FunzionarioValidator funzionarioValidator;

	@RequestMapping("/loginFunzionario")
	public String loginFunzionario(Model model) {
		model.addAttribute("funzionario", new Funzionario());
		model.addAttribute("ricerca", new Ricerca());
		return "loginFunzionario.html";
	}

	@RequestMapping(value = "/funzionario", method = RequestMethod.POST)
	public String newFunzionario(@Valid @ModelAttribute("funzionario") Funzionario funzionario, Model model,
			BindingResult bindingResult) {
		model.addAttribute("ricerca", new Ricerca());
		funzionarioValidator.validate(funzionario, bindingResult);
		if (!bindingResult.hasErrors()) {
			if (funzionario.getNome().toLowerCase().equals("danilo")
					&& funzionario.getPassword().toLowerCase().equals("password"))
				return "paginaIniziale.html";
			else
				return "loginFunzionario.html";
		} else
			return "loginFunzionario.html";
	}

	@RequestMapping(value = "/funzionario/{id}", method = RequestMethod.POST)
	public String getFunzionario(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			model.addAttribute("funzionario", funzionarioService.funzionarioPerId(id));
			return "funzionario.html";
		} else {
			model.addAttribute("funzionari", funzionarioService.tutti());
			return "funzionari.html";
		}
	}
}
