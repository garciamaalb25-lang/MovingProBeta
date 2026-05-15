package com.salesianostriana.dam.movingprobeta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.salesianostriana.dam.movingprobeta.Mudanza;
import com.salesianostriana.dam.movingprobeta.service.MudanzaService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mudanza")
@RequiredArgsConstructor
public class MudanzaController {

    private final MudanzaService mudanzaService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("mudanzas", mudanzaService.findAll());
        return "mudanza/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("mudanza", new Mudanza());
        return "mudanza/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("mudanza", mudanzaService.findById(id));
        return "mudanza/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Mudanza mudanza) {
        mudanzaService.save(mudanza);
        return "redirect:/mudanza";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        mudanzaService.deleteById(id);
        return "redirect:/mudanza";
    }
}