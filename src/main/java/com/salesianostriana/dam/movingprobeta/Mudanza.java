package com.salesianostriana.dam.movingprobeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

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

	@ManyToMany
    @JoinTable(name = "mudanza_operario",
        joinColumns = @JoinColumn(name = "mudanza_id"),
        inverseJoinColumns = @JoinColumn(name = "operario_id"))
    private List<Operario> operarios = new ArrayList<>();

    @OneToMany(mappedBy = "mudanza")
    private List<MudanzaVehiculo> vehiculos = new ArrayList<>();
}
	

