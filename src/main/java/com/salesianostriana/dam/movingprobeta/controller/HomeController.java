package com.salesianostriana.dam.movingprobeta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("totalMudanzas", mudanzaService.findAll().size());
        model.addAttribute("totalVehiculos", vehiculoService.findAll().size());
        model.addAttribute("totalOperarios", operarioService.findAll().size());
        model.addAttribute("vehiculosDisponibles", vehiculoService.findVehiculosDisponibles().size());
        return "index";
    }
}