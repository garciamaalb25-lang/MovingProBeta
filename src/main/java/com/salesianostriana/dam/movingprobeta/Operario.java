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
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Operario {
	@Id @GeneratedValue
	private Long idOperario;
	private String nombre;
	private int experiencia;
	
}
