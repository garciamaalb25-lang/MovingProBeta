package com.salesianostriana.dam.movingprobeta.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.movingprobeta.Mudanza;
import com.salesianostriana.dam.movingprobeta.exception.MudanzaNotFoundException;
import com.salesianostriana.dam.movingprobeta.EstadoMudanza;
import com.salesianostriana.dam.movingprobeta.repository.MudanzaRepository;
import lombok.RequiredArgsConstructor;

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

	public void deleteById(Long id) {
		mudanzaRepository.deleteById(id);
	}

	public List<Mudanza> findByFecha(LocalDate fecha) {
		return mudanzaRepository.findAll().stream().filter(m -> m.getFecha().equals(fecha))
				.collect(Collectors.toList());
	}

	public List<Mudanza> findMudanzasActivas() {
		return mudanzaRepository.findAll().stream().filter(m -> !m.getFecha().isBefore(LocalDate.now()))
				.collect(Collectors.toList());
	}

	public double calcularCosteTotal() {
		return mudanzaRepository.findAll().stream().mapToDouble(Mudanza::getCoste).sum();
	}
}