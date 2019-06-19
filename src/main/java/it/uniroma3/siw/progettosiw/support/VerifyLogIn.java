package it.uniroma3.siw.progettosiw.support;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

public class VerifyLogIn {

	public void addLoginAttributes(Model model) {
		if (verifyLogin()) {
			UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String role = details.getAuthorities().iterator().next().getAuthority();
			model.addAttribute("nome", details.getUsername());
			model.addAttribute("role", role);
		} else {
			model.addAttribute("nome", null);
			model.addAttribute("role", null);
		}
	}

	public boolean verifyLogin() {
		return (SecurityContextHolder.getContext().getAuthentication() != null)
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				// when Anonymous Authentication is enabled
				!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
}
