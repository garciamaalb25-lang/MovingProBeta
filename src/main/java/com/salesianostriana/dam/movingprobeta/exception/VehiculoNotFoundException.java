package com.salesianostriana.dam.movingprobeta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Excepción personalizada para indicar que no se encontró un vehículo con el id especificado
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehiculoNotFoundException extends RuntimeException {

	public VehiculoNotFoundException(Long id) {
		super("No se encontró ningún vehículo con id: " + id);
	}
}