package com.salesianostriana.dam.movingprobeta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
	private String matricula;
	private double capacidad;
	private boolean disponible;
	private double costePorHora;
	
}
