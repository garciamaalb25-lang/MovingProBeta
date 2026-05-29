package com.salesianostriana.dam.movingprobeta.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.movingprobeta.model.Mudanza;
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

	@GetMapping("/")
	public String index(Model model) {
		List<Mudanza> todasMudanzas = mudanzaService.findAll();
		List<Vehiculo> todosVehiculos = vehiculoService.findAll();

		model.addAttribute("totalMudanzas", todasMudanzas.size());
		model.addAttribute("totalVehiculos", todosVehiculos.size());
		model.addAttribute("totalOperarios", operarioService.findAll().size());

		model.addAttribute("vehiculosDisponibles", vehiculoService.findVehiculosDisponibles());
		model.addAttribute("ultimasMudanzas", todasMudanzas.stream().limit(5).collect(Collectors.toList()));
		model.addAttribute("ultimosOperarios",
				operarioService.findAll().stream().limit(5).collect(Collectors.toList()));

		double costeTotal = todasMudanzas.stream().mapToDouble(Mudanza::getCoste).sum();
		model.addAttribute("costeTotal", costeTotal);

		todasMudanzas.stream().max(Comparator.comparingDouble(Mudanza::getCoste))
				.ifPresent(m -> model.addAttribute("mudanzaMasCara", m));

		Vehiculo vehiculoMasUsado = todosVehiculos.stream().filter(v -> !v.isDisponible()).findFirst().orElse(null);
		model.addAttribute("vehiculoMasUsado", vehiculoMasUsado);

		long vehiculosDisponiblesCount = todosVehiculos.stream().filter(Vehiculo::isDisponible).count();
		int porcentajeDisponibles = todosVehiculos.isEmpty() ? 0
				: (int) (vehiculosDisponiblesCount * 100 / todosVehiculos.size());
		model.addAttribute("porcentajeDisponibles", porcentajeDisponibles);

		model.addAttribute("operarioMasExperimentado",
				operarioService.findAll().stream().max(Comparator.comparingInt(o -> o.getExperiencia())).orElse(null));

		return "index";
	}
}