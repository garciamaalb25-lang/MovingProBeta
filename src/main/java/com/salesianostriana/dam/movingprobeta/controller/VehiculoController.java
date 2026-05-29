package com.salesianostriana.dam.movingprobeta.controller;

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
        model.addAttribute("vehiculos", vehiculoService.findAll());
        return "vehiculo/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculo/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehiculo", vehiculoService.findById(id));
        return "vehiculo/form";
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
                                   @RequestParam(required = false) Double capacidadMinima,
                                   Model model) {
        if (disponible != null && disponible.equals("false")) {
            model.addAttribute("vehiculos", vehiculoService.findVehiculosNoDisponibles());
        } else if (disponible != null && disponible.equals("true")) {
            model.addAttribute("vehiculos", vehiculoService.findVehiculosDisponibles());
        } else if (capacidadMinima != null) {
            model.addAttribute("vehiculos", vehiculoService.findByCapacidadMinima(capacidadMinima));
        } else {
            model.addAttribute("vehiculos", vehiculoService.findAll());
        }
        return "vehiculo/list";
    }
}