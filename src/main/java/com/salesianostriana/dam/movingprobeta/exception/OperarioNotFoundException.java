package com.salesianostriana.dam.movingprobeta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Excepción personalizada para indicar que no se encontró un operario con el id especificado
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OperarioNotFoundException extends RuntimeException {

	public OperarioNotFoundException(Long id) {
		super("No se encontró ningún operario con id: " + id);
	}
}