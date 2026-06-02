package com.salesianostriana.dam.movingprobeta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.movingprobeta.exception.OperarioNotFoundException;
import com.salesianostriana.dam.movingprobeta.model.Operario;
import com.salesianostriana.dam.movingprobeta.repository.OperarioRepository;
import lombok.RequiredArgsConstructor;

// Servicio para la entidad Operario, con métodos para realizar operaciones CRUD y consultas personalizadas por experiencia y actividad
@Service
@RequiredArgsConstructor
public class OperarioService {

	private final OperarioRepository operarioRepository;

	public List<Operario> findAll() {
		return operarioRepository.findAll();
	}

	public Operario findById(Long id) {
		return operarioRepository.findById(id).orElseThrow(() -> new OperarioNotFoundException(id));
	}

	public Operario save(Operario operario) {
		return operarioRepository.save(operario);
	}

	public void deleteById(Long id) {
		operarioRepository.deleteById(id);
	}

// Buscar operarios con experiencia igual o superior a un número de años, ordenados de mayor a menor experiencia
	public List<Operario> findOperariosExperimentados(int anios) {
		return operarioRepository.findOperariosExperimentados(anios);
	}

// Buscar operarios que han participado en al menos una mudanza, indicando que están activos
	public List<Operario> findOperariosActivos() {
		return operarioRepository.findOperariosActivos();
	}

//	Buscar operarios con experiencia igual o superior a un número de años, ordenados de mayor a menor experiencia, utilizando Java Streams para filtrar y ordenar la lista completa de operarios
	public List<Operario> findByExperienciaMinima(int anios) {
		List<Operario> todosOperarios = operarioRepository.findAll();
		return todosOperarios.stream().filter(o -> o.getExperiencia() >= anios)
				.sorted((o1, o2) -> o2.getExperiencia() - o1.getExperiencia()).collect(Collectors.toList());
	}
}