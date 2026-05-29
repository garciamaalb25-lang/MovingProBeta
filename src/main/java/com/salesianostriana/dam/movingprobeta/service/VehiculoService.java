package com.salesianostriana.dam.movingprobeta.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

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
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new VehiculoNotFoundException(id));
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

    public List<Vehiculo> findVehiculosNoDisponibles() {
        return vehiculoRepository.findVehiculosNoDisponibles();
    }

    public List<Vehiculo> findByCapacidadMinima(double capacidadMinima) {
        return vehiculoRepository.findAll()
                .stream()
                .filter(v -> v.getCapacidad() >= capacidadMinima)
                .collect(Collectors.toList());
    }

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