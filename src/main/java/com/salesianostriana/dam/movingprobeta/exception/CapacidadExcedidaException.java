package com.salesianostriana.dam.movingprobeta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CapacidadExcedidaException extends RuntimeException {

    public CapacidadExcedidaException(double capacidadMaxima) {
        super("La capacidad del vehículo ha sido excedida. Capacidad máxima: " + capacidadMaxima);
    }
}