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
import com.salesianostriana.dam.movingprobeta.model.Mudanza;
import com.salesianostriana.dam.movingprobeta.model.MudanzaVehiculo;
import com.salesianostriana.dam.movingprobeta.model.Vehiculo;
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

// Controlador para manejar la asignación de vehículos a mudanzas, cambio de estado y eliminación de asignaciones
	@GetMapping
	public String list(Model model) {
		List<MudanzaVehiculo> asignaciones = mudanzaVehiculoService.findAll();
		model.addAttribute("asignaciones", asignaciones);
		return "mudanzavehiculo/mudanzavehiculo-list";
	}

// Mostrar formulario para crear nueva asignación de vehículo a mudanza
	@GetMapping("/new")
	public String newForm(Model model) {
		List<Mudanza> mudanzas = mudanzaService.findAll();
		List<Vehiculo> vehiculos = vehiculoService.findVehiculosDisponibles();
		model.addAttribute("mudanzas", mudanzas);
		model.addAttribute("vehiculos", vehiculos);
		return "mudanzavehiculo/mudanzavehiculo-form";
	}

// Guardar nueva asignación de vehículo a mudanza con cálculo de coste basado en peso
	@PostMapping("/save")
	public String save(@RequestParam Long mudanzaId, @RequestParam Long vehiculoId, @RequestParam double pesoMudanza,
			@RequestParam(required = false) String observaciones, RedirectAttributes redirectAttributes) {
		MudanzaVehiculo mv = mudanzaVehiculoService.asignarVehiculo(mudanzaId, vehiculoId, pesoMudanza, observaciones);
		double coste = mv.getMudanza().getCoste();
		redirectAttributes.addFlashAttribute("mensajeCoste",
				"Asignación guardada correctamente. Coste calculado: " + coste + " €");
		return "redirect:/mudanzavehiculo";
	}

// Mostrar formulario para cambiar estado de una asignación de vehículo a mudanza
	@GetMapping("/estado/{id}")
	public String cambiarEstadoForm(@PathVariable Long id, Model model) {
		MudanzaVehiculo asignacion = mudanzaVehiculoService.findById(id);
		EstadoMudanza[] estados = EstadoMudanza.values();
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("estados", estados);
		return "mudanzavehiculo/mudanzavehiculo-estado";
	}

// Cambiar estado de una asignación de vehículo a mudanza
	@PostMapping("/estado/{id}")
	public String cambiarEstado(@PathVariable Long id, @RequestParam EstadoMudanza estado) {
		mudanzaVehiculoService.cambiarEstado(id, estado);
		return "redirect:/mudanzavehiculo";
	}

//	Eliminar asignación de vehículo a mudanza por id
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		mudanzaVehiculoService.deleteById(id);
		return "redirect:/mudanzavehiculo";
	}
}