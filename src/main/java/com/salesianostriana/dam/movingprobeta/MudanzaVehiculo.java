package com.salesianostriana.dam.movingprobeta;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class MudanzaVehiculo {
	@Id @GeneratedValue
	private Long idMudanzaVehiculo;
	private LocalDateTime fechaAsignacion;
	private LocalDateTime fechaLiberacion;
	
}
