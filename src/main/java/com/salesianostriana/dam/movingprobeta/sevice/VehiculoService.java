package com.salesianostriana.dam.movingprobeta.sevice;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.movingprobeta.Vehiculo;
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
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con id: " + id));
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void deleteById(Long id) {
        vehiculoRepository.deleteById(id);
    }

    public List<Vehiculo> findVehiculosDisponibles() {
        return vehiculoRepository.findAll()
                .stream()
                .filter(Vehiculo::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Vehiculo> findByCapacidadMinima(double capacidadMinima) {
        return vehiculoRepository.findAll()
                .stream()
                .filter(v -> v.getCapacidad() >= capacidadMinima)
                .collect(Collectors.toList());
    }
}