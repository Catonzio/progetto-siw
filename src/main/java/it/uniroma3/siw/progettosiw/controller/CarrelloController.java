package it.uniroma3.siw.progettosiw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progettosiw.model.Carrello;
import it.uniroma3.siw.progettosiw.model.Foto;
import it.uniroma3.siw.progettosiw.model.Richiesta;
import it.uniroma3.siw.progettosiw.service.FotoService;
import it.uniroma3.siw.progettosiw.service.RichiestaService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
@Scope("session")
public class CarrelloController {

	private Carrello carrello = new Carrello();

	@Autowired
	private FotoService fotoService;

	@Autowired
	private RichiestaService richiestaService;

	private VerifyLogIn verify = new VerifyLogIn();

	@RequestMapping("/carrello")
	public String showCarrello(Model model) {
		verify.addLoginAttributes(model);
		model.addAttribute("fotografie", carrello.getFotoCarrello());
		return "carrello.html";
	}

	@RequestMapping("/richiestaUtilizzo")
	public String iniziaRichiesta(Model model) {
		Richiesta richiesta = new Richiesta();
		richiesta.setFotoRichieste(carrello.getFotoCarrello());
		richiestaService.save(richiesta);
		model.addAttribute("id", richiesta.getId());
		return "richiestaUtilizzo.html";
	}

	@RequestMapping(value = "/carrello/{id}", method = RequestMethod.GET)
	public String addToCarrello(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		if (id != null) {
			Foto foto = fotoService.trovaPerId(id);
			carrello.aggiungiFoto(foto);
		}
		model.addAttribute("fotografie", carrello.getFotoCarrello());
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@RequestMapping(value = "/svuotaCarrello/{id}", method = RequestMethod.GET)
	public String deleteElementFromCarrello(@PathVariable("id") Long id, Model model) {
		verify.addLoginAttributes(model);
		if (id != null) {
			Foto foto = fotoService.trovaPerId(id);
			carrello.deleteFoto(foto);
		}
		model.addAttribute("fotografie", carrello.getFotoCarrello());
		return "carrello.html";
	}

	@RequestMapping("/svuotaCarrello")
	public String svuotaCarrello(Model model) {
		verify.addLoginAttributes(model);
		carrello.svuota();
		model.addAttribute("fotografie", carrello.getFotoCarrello());
		return "carrello.html";
	}

}
