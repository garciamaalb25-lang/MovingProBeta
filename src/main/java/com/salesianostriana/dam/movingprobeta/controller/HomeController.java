package com.salesianostriana.dam.movingprobeta.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.salesianostriana.dam.movingprobeta.model.Mudanza;
import com.salesianostriana.dam.movingprobeta.model.Operario;
import com.salesianostriana.dam.movingprobeta.model.Vehiculo;
import com.salesianostriana.dam.movingprobeta.service.MudanzaService;
import com.salesianostriana.dam.movingprobeta.service.OperarioService;
import com.salesianostriana.dam.movingprobeta.service.VehiculoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MudanzaService mudanzaService;
	private final VehiculoService vehiculoService;
	private final OperarioService operarioService;
	// Controlador para la página de inicio que muestra estadísticas y resúmenes de
	// mudanzas, vehículos y operarios

	@GetMapping("/")
	public String index(Model model) {
		List<Mudanza> todasMudanzas = mudanzaService.findAll();
		List<Vehiculo> todosVehiculos = vehiculoService.findAll();
		List<Operario> todosOperarios = operarioService.findAll();
// Estadísticas y resúmenes para mostrar en la página de inicio
		long vehiculosDisponiblesCount = todosVehiculos.stream().filter(Vehiculo::isDisponible).count();
		int porcentajeDisponibles = todosVehiculos.isEmpty() ? 0
				: (int) (vehiculosDisponiblesCount * 100 / todosVehiculos.size());
		double costeTotal = todasMudanzas.stream().mapToDouble(Mudanza::getCoste).sum();
		Mudanza mudanzaMasCara = todasMudanzas.stream().max(Comparator.comparingDouble(Mudanza::getCoste)).orElse(null);
		Vehiculo vehiculoMasUsado = todosVehiculos.stream().filter(v -> !v.isDisponible()).findFirst().orElse(null);
		Operario operarioMasExperimentado = todosOperarios.stream()
				.max(Comparator.comparingInt(Operario::getExperiencia)).orElse(null);
// Agregar atributos al modelo para mostrar en la vista
		model.addAttribute("totalMudanzas", todasMudanzas.size());
		model.addAttribute("totalVehiculos", todosVehiculos.size());
		model.addAttribute("totalOperarios", todosOperarios.size());
		model.addAttribute("vehiculosDisponibles", vehiculoService.findVehiculosDisponibles());
		model.addAttribute("ultimasMudanzas", todasMudanzas.stream().limit(5).collect(Collectors.toList()));
		model.addAttribute("ultimosOperarios", todosOperarios.stream().limit(5).collect(Collectors.toList()));
		model.addAttribute("costeTotal", costeTotal);
		model.addAttribute("mudanzaMasCara", mudanzaMasCara);
		model.addAttribute("vehiculoMasUsado", vehiculoMasUsado);
		model.addAttribute("porcentajeDisponibles", porcentajeDisponibles);
		model.addAttribute("operarioMasExperimentado", operarioMasExperimentado);

		return "index";
	}
}