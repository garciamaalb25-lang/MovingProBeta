package com.salesianostriana.dam.movingprobeta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Excepción personalizada para indicar que un vehículo no está disponible
@ResponseStatus(HttpStatus.CONFLICT)
public class VehiculoNoDisponibleException extends RuntimeException {

	public VehiculoNoDisponibleException(String matricula) {
		super("El vehículo con matrícula " + matricula + " no está disponible");
	}
}