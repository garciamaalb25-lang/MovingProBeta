package com.salesianostriana.dam.movingprobeta.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.movingprobeta.model.EstadoMudanza;
import com.salesianostriana.dam.movingprobeta.model.MudanzaVehiculo;
import com.salesianostriana.dam.movingprobeta.service.MudanzaService;
import com.salesianostriana.dam.movingprobeta.service.MudanzaVehiculoService;
import com.salesianostriana.dam.movingprobeta.service.VehiculoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mudanzavehiculo")
@RequiredArgsConstructor
public class MudanzaVehiculoController {

	private final MudanzaVehiculoService mudanzaVehiculoService;
	private final MudanzaService mudanzaService;
	private final VehiculoService vehiculoService;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("asignaciones", mudanzaVehiculoService.findAll());
		return "mudanzavehiculo/list";
	}

	@GetMapping("/new")
	public String newForm(Model model) {
		model.addAttribute("mudanzas", mudanzaService.findAll());
		model.addAttribute("vehiculos", vehiculoService.findVehiculosDisponibles());
		return "mudanzavehiculo/form";
	}

	@PostMapping("/save")
	public String save(@RequestParam Long mudanzaId, @RequestParam Long vehiculoId, @RequestParam double pesoMudanza,
			@RequestParam(required = false) String observaciones, RedirectAttributes redirectAttributes) {
		MudanzaVehiculo mv = mudanzaVehiculoService.asignarVehiculo(mudanzaId, vehiculoId, pesoMudanza, observaciones);
		double coste = mv.getMudanza().getCoste();
		redirectAttributes.addFlashAttribute("mensajeCoste",
				"Asignación guardada correctamente. Coste calculado: " + coste + " €");
		return "redirect:/mudanzavehiculo";
	}

	@GetMapping("/estado/{id}")
	public String cambiarEstadoForm(@PathVariable Long id, Model model) {
		model.addAttribute("asignacion", mudanzaVehiculoService.findById(id));
		model.addAttribute("estados", EstadoMudanza.values());
		return "mudanzavehiculo/estado";
	}

	@PostMapping("/estado/{id}")
	public String cambiarEstado(@PathVariable Long id, @RequestParam EstadoMudanza estado) {
		mudanzaVehiculoService.cambiarEstado(id, estado);
		return "redirect:/mudanzavehiculo";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		mudanzaVehiculoService.deleteById(id);
		return "redirect:/mudanzavehiculo";
	}
}