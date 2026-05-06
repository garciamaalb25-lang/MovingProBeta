package com.salesianostriana.dam.movingprobeta;

import java.time.LocalDate;
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
public class Mudanza {

	@Id @GeneratedValue
	private Long idMudanza;
	private int codigo;
	private String origen;
	private String destino;
	private double coste;
	private int numeroHoras;
	private LocalDate fecha;


	
}
