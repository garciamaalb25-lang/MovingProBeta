package com.salesianostriana.dam.movingprobeta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controlador para manejar la página de login
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}