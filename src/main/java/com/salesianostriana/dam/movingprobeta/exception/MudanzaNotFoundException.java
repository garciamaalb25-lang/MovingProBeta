package com.salesianostriana.dam.movingprobeta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MudanzaNotFoundException extends RuntimeException {

    public MudanzaNotFoundException(Long id) {
        super("No se encontró ninguna mudanza con id: " + id);
    }
}