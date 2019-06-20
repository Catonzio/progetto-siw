package it.uniroma3.siw.progettosiw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progettosiw.model.Fotografo;
import it.uniroma3.siw.progettosiw.service.FotografoService;
import it.uniroma3.siw.progettosiw.support.VerifyLogIn;
import it.uniroma3.siw.progettosiw.validator.FotografoValidator;

@Controller
public class FotografoController {

	@Autowired
	private FotografoService fotografoService;

	@Autowired
	private FotografoValidator fotografoValidator;

	private VerifyLogIn verify = new VerifyLogIn();

	@ModelAttribute
	public void addCommonAttributes(Model model) {
		model.addAttribute("fotografi", fotografoService.tuttiFotografi());
	}

	@RequestMapping(value = "/fotografi")
	public String fotografi(Model model) {
		List<Fotografo> fotografi = fotografoService.tuttiFotografi();
		model.addAttribute("fotografoSelezionato", fotografi.get(0));
		verify.addLoginAttributes(model);
		return "fotografi.html";
	}

	@RequestMapping("/addFotografo")
	public String addFotografo(Model model) {
		model.addAttribute("fotografo", new Fotografo());
		verify.addLoginAttributes(model);
		return "addFotografo.html";
	}

	@RequestMapping(value = "/aggiungiFotografo", method = RequestMethod.POST)
	public String aggiungiFotografo(@ModelAttribute("fotografo") Fotografo fotografo, Model model,
			BindingResult bindingResult) {
		verify.addLoginAttributes(model);
		fotografoValidator.validate(fotografo, bindingResult);
		if (!bindingResult.hasErrors()) {
			fotografoService.save(fotografo);
			model.addAttribute("fotografoSelezionato", fotografoService.tuttiFotografi().get(0));
			model.addAttribute("fotografi", fotografoService.tuttiFotografi());
			return "fotografi.html";
		} else
			return "addFotografo.html";
	}

	@RequestMapping(value = "/fotografo/{id}", method = RequestMethod.GET)
	public String visualizzaFotografo(@PathVariable("id") Long id, Model model) {
		verify.addLoginAttributes(model);
		if (id != null) {
			Fotografo fotografo = fotografoService.trovaPerId(id);
			model.addAttribute("fotografoSelezionato", fotografo);
			if (!(fotografo.getAlbum().size() == 0)) {
				model.addAttribute("albumi", fotografo.getAlbum());
			}
			return "fotografi.html";
		} else
			return "fotografi.html";
	}
}
