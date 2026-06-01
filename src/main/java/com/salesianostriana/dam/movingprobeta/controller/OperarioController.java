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
import com.salesianostriana.dam.movingprobeta.model.Operario;
import com.salesianostriana.dam.movingprobeta.service.OperarioService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/operario")
@RequiredArgsConstructor
public class OperarioController {

	private final OperarioService operarioService;

// Controlador para manejar las operaciones CRUD de operarios, búsqueda por experiencia y listado de operarios en la página de inicio
	@GetMapping
	public String list(Model model) {
		List<Operario> operarios = operarioService.findAll();
		model.addAttribute("operarios", operarios);
		return "operario/operario-list";
	}

// Mostrar formulario para crear nuevo operario
	@GetMapping("/new")
	public String newForm(Model model) {
		Operario operario = new Operario();
		model.addAttribute("operario", operario);
		return "operario/operario-form";
	}

// Mostrar formulario para editar operario existente
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Operario operario = operarioService.findById(id);
		model.addAttribute("operario", operario);
		return "operario/operario-form";
	}

// Guardar nuevo operario o actualizar existente
	@PostMapping("/save")
	public String save(@ModelAttribute Operario operario) {
		operarioService.save(operario);
		return "redirect:/operario";
	}

// Eliminar operario por id
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		operarioService.deleteById(id);
		return "redirect:/operario";
	}

// Buscar operarios por experiencia mínima y mostrar resultados en la vista
	@GetMapping("/buscar")
	public String buscarOperarios(@RequestParam(required = false) Integer experienciaMinima, Model model) {
		List<Operario> operarios;
		if (experienciaMinima != null) {
			operarios = operarioService.findOperariosExperimentados(experienciaMinima);
		} else {
			operarios = operarioService.findAll();
		}
		model.addAttribute("operarios", operarios);
		return "operario/operario-list";
	}
}