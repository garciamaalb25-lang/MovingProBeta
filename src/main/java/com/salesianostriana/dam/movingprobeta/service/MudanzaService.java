package com.salesianostriana.dam.movingprobeta.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.salesianostriana.dam.movingprobeta.exception.MudanzaNotFoundException;
import com.salesianostriana.dam.movingprobeta.model.Mudanza;
import com.salesianostriana.dam.movingprobeta.repository.MudanzaRepository;
import lombok.RequiredArgsConstructor;

// Servicio para la entidad Mudanza, con métodos para realizar operaciones CRUD y consultas personalizadas por fecha y rango de fechas
@Service
@RequiredArgsConstructor
public class MudanzaService {

	private final MudanzaRepository mudanzaRepository;

	public List<Mudanza> findAll() {
		return mudanzaRepository.findAll();
	}

	public Mudanza findById(Long id) {
		return mudanzaRepository.findById(id).orElseThrow(() -> new MudanzaNotFoundException(id));
	}

	public Mudanza save(Mudanza mudanza) {
		return mudanzaRepository.save(mudanza);
	}

	@Transactional
	public void deleteById(Long id) {
		mudanzaRepository.deleteById(id);
	}

// Buscar mudanzas que tienen una fecha específica, utilizando una consulta personalizada en el repositorio
	public List<Mudanza> findByFecha(LocalDate fecha) {
		return mudanzaRepository.findByFecha(fecha);
	}

// Buscar mudanzas que tienen fecha entre un rango de fechas, utilizando una consulta personalizada en el repositorio
	public List<Mudanza> findByFechaEntreRango(LocalDate fechaInicio, LocalDate fechaFin) {
		return mudanzaRepository.findByFechaEntreRango(fechaInicio, fechaFin);
	}

// Buscar mudanzas que tienen fecha igual o posterior a la fecha actual, indicando que están activas
	public List<Mudanza> findMudanzasActivas() {
		List<Mudanza> todasMudanzas = mudanzaRepository.findAll();
		return todasMudanzas.stream().filter(m -> !m.getFecha().isBefore(LocalDate.now())).collect(Collectors.toList());
	}

// Calcular el coste total de todas las mudanzas registradas, sumando el coste de cada mudanza utilizando Java Streams
	public double calcularCosteTotal() {
		List<Mudanza> todasMudanzas = mudanzaRepository.findAll();
		return todasMudanzas.stream().mapToDouble(Mudanza::getCoste).sum();
	}
}