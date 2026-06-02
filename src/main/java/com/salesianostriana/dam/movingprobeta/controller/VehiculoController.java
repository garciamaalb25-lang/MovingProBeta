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
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
public class VehiculoController {

	private final VehiculoService vehiculoService;
// Controlador para manejar las operaciones CRUD de vehículos, búsqueda por disponibilidad y capacidad mínima, y listado de vehículos en la página de inicio
	@GetMapping
	public String list(Model model) {
		List<Vehiculo> vehiculos = vehiculoService.findAll();
		model.addAttribute("vehiculos", vehiculos);
		return "vehiculo/vehiculo-list";
	}
// Mostrar formulario para crear nuevo vehículo
	@GetMapping("/new")
	public String newForm(Model model) {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setDisponible(true);
		model.addAttribute("vehiculo", vehiculo);
		model.addAttribute("isAssigned", false);
		return "vehiculo/vehiculo-form";
	}
// Mostrar formulario para editar vehículo existente
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Vehiculo vehiculo = vehiculoService.findById(id);
		model.addAttribute("vehiculo", vehiculo);
		model.addAttribute("isAssigned", !vehiculo.isDisponible());
		return "vehiculo/vehiculo-form";
	}
// Guardar nuevo vehículo o actualizar existente
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Vehiculo vehiculo, BindingResult result, Model model) {
		boolean isAssigned = false;
		if (vehiculo.getIdVehiculo() != null) {
			Vehiculo existing = vehiculoService.findById(vehiculo.getIdVehiculo());
			isAssigned = !existing.isDisponible();
			if (isAssigned) {
				if (existing.getCapacidad() != vehiculo.getCapacidad()) {
					result.rejectValue("capacidad", "error.vehiculo", "No se puede cambiar la capacidad de un vehículo asignado a una mudanza activa.");
				}
				if (existing.getCostePorHora() != vehiculo.getCostePorHora()) {
					result.rejectValue("costePorHora", "error.vehiculo", "No se puede cambiar el coste por hora de un vehículo asignado a una mudanza activa.");
				}
				if (!existing.getMatricula().equals(vehiculo.getMatricula())) {
					result.rejectValue("matricula", "error.vehiculo", "No se puede cambiar la matrícula de un vehículo asignado a una mudanza activa.");
				}
				if (vehiculo.isDisponible()) {
					result.rejectValue("disponible", "error.vehiculo", "No se puede cambiar la disponibilidad manualmente si el vehículo está asignado.");
				}
			}
		}

		if (result.hasErrors()) {
			model.addAttribute("isAssigned", isAssigned);
			return "vehiculo/vehiculo-form";
		}
		vehiculoService.save(vehiculo);
		return "redirect:/vehiculo";
	}
// Eliminar vehículo por id
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		vehiculoService.deleteById(id);
		return "redirect:/vehiculo";
	}
// Buscar vehículos por disponibilidad y capacidad mínima, y mostrar resultados en la vista
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