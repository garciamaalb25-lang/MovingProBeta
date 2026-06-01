package com.salesianostriana.dam.movingprobeta.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Controlador de consejos para manejar excepciones de forma global en la aplicación
@ControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(CapacidadExcedidaException.class)
	public String handleCapacidadExcedida(CapacidadExcedidaException ex, Model model) {
		model.addAttribute("errorTitulo", "Capacidad Excedida");
		model.addAttribute("errorMensaje", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(VehiculoNoDisponibleException.class)
	public String handleVehiculoNoDisponible(VehiculoNoDisponibleException ex, Model model) {
		model.addAttribute("errorTitulo", "Vehículo No Disponible");
		model.addAttribute("errorMensaje", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(MudanzaNotFoundException.class)
	public String handleMudanzaNotFound(MudanzaNotFoundException ex, Model model) {
		model.addAttribute("errorTitulo", "Mudanza No Encontrada");
		model.addAttribute("errorMensaje", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(OperarioNotFoundException.class)
	public String handleOperarioNotFound(OperarioNotFoundException ex, Model model) {
		model.addAttribute("errorTitulo", "Operario No Encontrado");
		model.addAttribute("errorMensaje", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(VehiculoNotFoundException.class)
	public String handleVehiculoNotFound(VehiculoNotFoundException ex, Model model) {
		model.addAttribute("errorTitulo", "Vehículo No Encontrado");
		model.addAttribute("errorMensaje", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String handleGeneral(Exception ex, Model model) {
		model.addAttribute("errorTitulo", "Error Inesperado");
		model.addAttribute("errorMensaje", "Ha ocurrido un error inesperado. Por favor, inténtalo de nuevo.");
		return "error";
	}
}