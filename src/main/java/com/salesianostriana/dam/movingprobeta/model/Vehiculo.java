package com.salesianostriana.dam.movingprobeta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Vehiculo {

    @Id @GeneratedValue
    private Long idVehiculo;

    @NotBlank(message = "La matrícula no puede estar vacía")
    @Pattern(regexp = "^[0-9]{4}[A-Z]{3}$", message = "La matrícula debe tener 4 números y 3 letras mayúsculas (ej: 1234ABC)")
    private String matricula;

    @Min(value = 100, message = "La capacidad mínima es 100 kg")
    @Max(value = 30000, message = "La capacidad máxima es 30000 kg")
    private double capacidad;

    private boolean disponible;

    @Min(value = 10, message = "El coste mínimo por hora es 10 €")
    @Max(value = 500, message = "El coste máximo por hora es 500 €")
    private double costePorHora;
}