package com.salesianostriana.dam.movingprobeta.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.salesianostriana.dam.movingprobeta.model.Vehiculo;
import com.salesianostriana.dam.movingprobeta.service.VehiculoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
public class VehiculoController {

	private final VehiculoService vehiculoService;

	@GetMapping
	public String list(Model model) {
		List<Vehiculo> vehiculos = vehiculoService.findAll();
		model.addAttribute("vehiculos", vehiculos);
		return "vehiculo/vehiculo-list";
	}

	@GetMapping("/new")
	public String newForm(Model model) {
		Vehiculo vehiculo = new Vehiculo();
		model.addAttribute("vehiculo", vehiculo);
		return "vehiculo/vehiculo-form";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Vehiculo vehiculo = vehiculoService.findById(id);
		model.addAttribute("vehiculo", vehiculo);
		return "vehiculo/vehiculo-form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Vehiculo vehiculo) {
		vehiculoService.save(vehiculo);
		return "redirect:/vehiculo";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		vehiculoService.deleteById(id);
		return "redirect:/vehiculo";
	}

	@GetMapping("/buscar")
	public String buscarVehiculos(@RequestParam(required = false) String disponible,
			@RequestParam(required = false) Double capacidadMinima, Model model) {
		List<Vehiculo> vehiculos;
		if (disponible != null && disponible.equals("false")) {
			vehiculos = vehiculoService.findVehiculosNoDisponibles();
		} else if (disponible != null && disponible.equals("true")) {
			vehiculos = vehiculoService.findVehiculosDisponibles();
		} else if (capacidadMinima != null) {
			vehiculos = vehiculoService.findByCapacidadMinima(capacidadMinima);
		} else {
			vehiculos = vehiculoService.findAll();
		}
		model.addAttribute("vehiculos", vehiculos);
		return "vehiculo/vehiculo-list";
	}
}