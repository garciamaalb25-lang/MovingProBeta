package com.salesianostriana.dam.movingprobeta.controller;

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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("operarios", operarioService.findAll());
        return "operario/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("operario", new Operario());
        return "operario/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("operario", operarioService.findById(id));
        return "operario/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Operario operario) {
        operarioService.save(operario);
        return "redirect:/operario";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        operarioService.deleteById(id);
        return "redirect:/operario";
    }
    
    @GetMapping("/buscar")
    public String buscarOperarios(@RequestParam(required = false) Integer experienciaMinima,
                                   Model model) {
        if (experienciaMinima != null) {
            model.addAttribute("operarios", operarioService.findOperariosExperimentados(experienciaMinima));
        } else {
            model.addAttribute("operarios", operarioService.findAll());
        }
        return "operario/list";
    }
}