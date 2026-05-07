package com.salesianostriana.dam.movingprobeta;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String operaciones;
	@ManyToOne
	@JoinColumn(name = "mudanza_id")
	private Mudanza mudanza;
	
	@ManyToOne
	@JoinColumn(name = "vehiculo_id")
	private Mudanza vehiculo;
	
	@Enumerated(EnumType.STRING)
	private EstadoMudanza estado;
}                      