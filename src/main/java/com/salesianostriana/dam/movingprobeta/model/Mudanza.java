package com.salesianostriana.dam.movingprobeta.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mudanza {

	@Id
	@GeneratedValue
	private Long idMudanza;

	@Min(value = 1, message = "El código debe ser mayor que 0")
	@Max(value = 99999, message = "El código no puede superar 99999")
	private int codigo;

	@NotBlank(message = "El origen no puede estar vacío")
	@Size(min = 3, max = 100, message = "El origen debe tener entre 3 y 100 caracteres")
	private String origen;

	@NotBlank(message = "El destino no puede estar vacío")
	@Size(min = 3, max = 100, message = "El destino debe tener entre 3 y 100 caracteres")
	private String destino;

	private double coste;

	@Min(value = 1, message = "El mínimo es 1 hora")
	@Max(value = 720, message = "El máximo es 720 horas")
	private int numeroHoras;

	@NotNull(message = "La fecha no puede estar vacía")
	private LocalDate fecha;

	@ManyToMany
	@JoinTable(name = "mudanza_operario", joinColumns = @JoinColumn(name = "mudanza_id"), inverseJoinColumns = @JoinColumn(name = "operario_id"))
	private List<Operario> operarios = new ArrayList<>();

	@OneToMany(mappedBy = "mudanza")
	@ToString.Exclude
	private List<MudanzaVehiculo> vehiculos = new ArrayList<>();
}