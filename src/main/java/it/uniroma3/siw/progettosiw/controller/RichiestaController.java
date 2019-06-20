package it.uniroma3.siw.progettosiw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progettosiw.model.Richiesta;
import it.uniroma3.siw.progettosiw.service.RichiestaService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;

@Controller
public class RichiestaController {

	@Autowired
	private RichiestaService richiestaService;

	private VerifyLogIn verify = new VerifyLogIn();

	@RequestMapping("/invioRichiesta/{id}")
	public String confermaInvio(@RequestParam("nomeRichiedente") String nome,
			@RequestParam("cognomeRichiedente") String cognome, @RequestParam("emailRichiedente") String email,
			@RequestParam("motivoRichiesta") String motivo, Model model, @PathVariable("id") Long id) {
		verify.addLoginAttributes(model);
		if (id != null) {
			Richiesta richiesta = richiestaService.trovaPerId(id);
			richiesta.setNomeRichiedente(nome);
			richiesta.setCognomeRichiedente(cognome);
			richiesta.setEmailRichiedente(email);
			richiesta.setTestoRichiesta(motivo);
			richiestaService.save(richiesta);
			model.addAttribute("richiesta", richiesta);
			return "confermaRichiesta.html";
		} else
			return "richiestaUtilizzo.html";
	}

	@RequestMapping("/vediRichieste")
	public String showRichieste(Model model) {
		verify.addLoginAttributes(model);
		model.addAttribute("richieste", richiestaService.tutteRichieste());
		return "visualizzaRichieste.html";
	}
}
