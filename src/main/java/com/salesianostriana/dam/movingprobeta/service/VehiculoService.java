package com.salesianostriana.dam.movingprobeta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.salesianostriana.dam.movingprobeta.exception.CapacidadExcedidaException;
import com.salesianostriana.dam.movingprobeta.exception.VehiculoNoDisponibleException;
import com.salesianostriana.dam.movingprobeta.exception.VehiculoNotFoundException;
import com.salesianostriana.dam.movingprobeta.model.Vehiculo;
import com.salesianostriana.dam.movingprobeta.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoService {

	private final VehiculoRepository vehiculoRepository;

	public List<Vehiculo> findAll() {
		return vehiculoRepository.findAll();
	}

	public Vehiculo findById(Long id) {
		return vehiculoRepository.findById(id).orElseThrow(() -> new VehiculoNotFoundException(id));
	}

	public Vehiculo save(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Transactional
	public void deleteById(Long id) {
		vehiculoRepository.deleteById(id);
	}

// Buscar vehículos que están disponibles, indicando que no están asignados a ninguna mudanza
	public List<Vehiculo> findVehiculosDisponibles() {
		List<Vehiculo> todosVehiculos = vehiculoRepository.findAll();
		return todosVehiculos.stream().filter(Vehiculo::isDisponible).collect(Collectors.toList());
	}

// Buscar vehículos que no están disponibles, indicando que están asignados a una mudanza
	public List<Vehiculo> findVehiculosNoDisponibles() {
		return vehiculoRepository.findVehiculosNoDisponibles();
	}

// Buscar vehículos con capacidad igual o superior a un valor mínimo, utilizando Java Streams para filtrar la lista completa de vehículos
	public List<Vehiculo> findByCapacidadMinima(double capacidadMinima) {
		List<Vehiculo> todosVehiculos = vehiculoRepository.findAll();
		return todosVehiculos.stream().filter(v -> v.getCapacidad() >= capacidadMinima).collect(Collectors.toList());
	}

	// Asignar un vehículo a una mudanza, calculando el coste basado en el peso de
	// la mudanza y validando la disponibilidad del vehículo y su capacidad
	public Vehiculo asignarVehiculo(Long idVehiculo, double pesoMudanza) {
		Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
				.orElseThrow(() -> new VehiculoNotFoundException(idVehiculo));
		if (!vehiculo.isDisponible()) {
			throw new VehiculoNoDisponibleException(vehiculo.getMatricula());
		}
		if (pesoMudanza > vehiculo.getCapacidad()) {
			throw new CapacidadExcedidaException(vehiculo.getCapacidad());
		}
		vehiculo.setDisponible(false);
		return vehiculoRepository.save(vehiculo);
	}
}